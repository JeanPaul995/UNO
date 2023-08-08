
package unoonline1;

/**
 *
 * @author sugam
 */
import java.util.*;

public class UnoOnline {
    private final Deck deck;
    private final List<Player> players;
    private final List<Card> discardPile;
    private Card topCard;
    private Player currentPlayer;
    private boolean gameActive;

    public UnoOnline(List<String> playerNames) {
        deck = new Deck();
        players = new ArrayList<>();
        discardPile = new ArrayList<>();

        // Create player instances based on player names
        for (String name : playerNames) {
            players.add(new Player(name));
        }

        gameActive = false;
    }
    
    private boolean canPlayCard(Player player, Card cardToPlay) {
        if (!cardToPlay.canBePlayedOn(topCard)) {
            System.out.println("Invalid move! The selected card cannot be played.");
            return false;
        }
        if (player.getHandSize() == 2 && !player.hasDeclaredUno()) {
            System.out.println("You must say 'Uno' when you have only one card left in your hand!");
            return false;
        }
        return true;
    }
    
    

    private void initializeGame() {
        deck.shuffle();
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                Card card = deck.drawCard();
                player.drawCard(card);
            }
        }

        topCard = deck.drawCard();
        discardPile.add(topCard);
    }

    public void playGame() {
        initializeGame();
        currentPlayer = players.get(0);
        gameActive = true;

        while (gameActive) {
            displayGameState();

            handleUnoDeclaration(currentPlayer);

            if (canPlayerPlay(currentPlayer)) {
                int cardIndex = currentPlayer instanceof ComputerPlayer ?
                        ((ComputerPlayer) currentPlayer).chooseCardToPlay(topCard) :
                        getPlayerCardIndexToPlay(currentPlayer);

                while (cardIndex < 0 || cardIndex >= currentPlayer.getHandSize()) {
                    System.out.println("Invalid card index. Please try again.");
                    cardIndex = currentPlayer instanceof ComputerPlayer ?
                            ((ComputerPlayer) currentPlayer).chooseCardToPlay(topCard) :
                            getPlayerCardIndexToPlay(currentPlayer);
                }

                Card cardToPlay = currentPlayer.getHand().get(cardIndex);
                while (!canPlayCard(currentPlayer, cardToPlay)) {
                    System.out.println("Cannot play the selected card. Try again.");
                    cardIndex = currentPlayer instanceof ComputerPlayer ?
                            ((ComputerPlayer) currentPlayer).chooseCardToPlay(topCard) :
                            getPlayerCardIndexToPlay(currentPlayer);

                    while (cardIndex < 0 || cardIndex >= currentPlayer.getHandSize()) {
                        System.out.println("Invalid card index. Please try again.");
                        cardIndex = currentPlayer instanceof ComputerPlayer ?
                                ((ComputerPlayer) currentPlayer).chooseCardToPlay(topCard) :
                                getPlayerCardIndexToPlay(currentPlayer);
                    }

                    cardToPlay = currentPlayer.getHand().get(cardIndex);
                }

                handleSpecialCard(cardToPlay);
                playCard(cardToPlay);

                if (currentPlayer.getHandSize() == 0) {
                    displayGameState();
                    handleGameOver(currentPlayer);
                }

                currentPlayer = getNextPlayer(currentPlayer);
            } else {
                // Draw a card if the player cannot play any card
                
                if (deck.isEmpty()) {
                    // Shuffle the discard pile (except the top card) back into the deck
                    Card firstCard = discardPile.remove(discardPile.size() - 1);
                    Collections.shuffle(discardPile);
                    discardPile.clear();
                    discardPile.add(firstCard);
                }
                
                Card drawnCard = deck.drawCard();
        currentPlayer.drawCard(drawnCard);
        System.out.println(currentPlayer.getName() + " drew a card: " + drawnCard);

        handleForgotUno(currentPlayer);

                currentPlayer = getNextPlayer(currentPlayer);
            }
        }
    } 
    private void handleForgotUno(Player player) {
    if (player.getHandSize() == 1 && !player.hasDeclaredUno()) {
        Card drawnCard1 = deck.drawCard();
        Card drawnCard2 = deck.drawCard();
        player.drawCard(drawnCard1);
        player.drawCard(drawnCard2);
        System.out.println(player.getName() + " forgot to say 'Uno' and drew 2 penalty cards: " +
                drawnCard1 + ", " + drawnCard2);
    }
}
    
    private void handleUnoDeclaration(Player player) {
    if (player.getHandSize() == 1 && !player.hasDeclaredUno()) {
        System.out.println(player.getName() + " has only one card left! Don't forget to say 'Uno'!");
    }
}

    private Player getNextPlayer(Player currentPlayer) {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        return players.get(nextIndex);
    }

    private boolean canPlayerPlay(Player player) {
        for (Card card : player.getHand()) {
            if (card.canBePlayedOn(topCard)) {
                return true;
            }
        }
        return false;
    }

    private int getPlayerCardIndexToPlay(Player player) {
        displayPlayerHand(player);
        Scanner scanner = new Scanner(System.in);
        int cardIndex = -1;

        while (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            System.out.print("Enter the index of the card to play (or -1 to draw a card): ");
            try {
                cardIndex = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume the invalid input
                System.out.println("Invalid input. Please enter a valid card index.");
            }
        }

        return cardIndex;
    }

    private void handleSpecialCard(Card card) {
        switch (card.getValue()) {
            case UnoConstants.DRAW_TWO:
                handleDrawTwoCard();
                break;
            case UnoConstants.SKIP:
                handleSkipCard();
                break;
            case UnoConstants.REVERSE:
                handleReverseCard();
                break;
            case UnoConstants.WILD:
            case UnoConstants.WILD_DRAW_FOUR:
                handleWildCard(card);
                break;
            default:
                // For regular cards, do nothing
                break;
        }
    }

    private void handleDrawTwoCard() {
        Player nextPlayer = getNextPlayer(getNextPlayer(currentPlayer));
        Card drawnCard1 = deck.drawCard();
        Card drawnCard2 = deck.drawCard();
        nextPlayer.drawCard(drawnCard1);
        nextPlayer.drawCard(drawnCard2);
        System.out.println(nextPlayer.getName() + " drew 2 cards: " + drawnCard1 + ", " + drawnCard2);
    }

    private void handleSkipCard() {
        currentPlayer = getNextPlayer(currentPlayer);
        System.out.println(currentPlayer.getName() + " is skipped!");
    }

    private void handleReverseCard() {
        Collections.reverse(players);
        System.out.println("Order of play reversed!");
    }

    private void handleWildCard(Card wildCard) {
        Scanner scanner = new Scanner(System.in);
        int chosenColorIndex = -1;

        if (wildCard.getValue().equals(UnoConstants.WILD_DRAW_FOUR)) {
            Player nextPlayer = getNextPlayer(currentPlayer);
            Card drawnCard1 = deck.drawCard();
            Card drawnCard2 = deck.drawCard();
            Card drawnCard3 = deck.drawCard();
            Card drawnCard4 = deck.drawCard();
            nextPlayer.drawCard(drawnCard1);
            nextPlayer.drawCard(drawnCard2);
            nextPlayer.drawCard(drawnCard3);
            nextPlayer.drawCard(drawnCard4);
            System.out.println(nextPlayer.getName() + " drew 4 cards: " +
                    drawnCard1 + ", " + drawnCard2 + ", " + drawnCard3 + ", " + drawnCard4);
        }

        while (chosenColorIndex < 0 || chosenColorIndex >= UnoConstants.COLORS.length) {
            System.out.println("Choose a color for the Wild card:");
            for (int i = 0; i < UnoConstants.COLORS.length; i++) {
                System.out.println((i + 1) + ". " + UnoConstants.COLORS[i]);
            }
            System.out.print("Enter the number corresponding to the color: ");
            try {
                chosenColorIndex = scanner.nextInt() - 1;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume the invalid input
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        wildCard.setColor(UnoConstants.COLORS[chosenColorIndex]);
    }
    

    private void displayGameState() {
    System.out.println("Top card on discard pile: " + topCard);
    System.out.println("Current player: " + currentPlayer.getName());
    System.out.println("Your Hand:");
    displayPlayerHand(currentPlayer);
}


    private void displayPlayerHand(Player player) {
        List<Card> hand = player.getHand();
        System.out.println(player.getName() + "'s Hand: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ". " + hand.get(i));
        }
    }

    private void displayWinner(Player player) {
        System.out.println(player.getName() + " has won the game!");
    }
     private void playCard(Card cardToPlay) {
        currentPlayer.getHand().remove(cardToPlay);
        discardPile.add(cardToPlay);
        topCard = cardToPlay;
    }
    private void handleGameOver(Player player) {
        if (player.getHandSize() == 0) {
            displayWinner(player);
            gameActive = false;
        }
    }   

    private void handleWildDrawFourCard(Card wildCard) {
        Player nextPlayer = getNextPlayer(currentPlayer);
        for (int i = 0; i < 4; i++) {
            Card drawnCard = deck.drawCard();
            nextPlayer.drawCard(drawnCard);
            System.out.println(nextPlayer.getName() + " drew a card: " + drawnCard);
        }
        handleWildCard(wildCard);
    }
    

    public static void main(String[] args) {
        List<String> playerNames = Arrays.asList("Player 1", "Player 2", "Player 3"); // Add as many players as you want.
        UnoOnline unoGame = new UnoOnline(playerNames);
        unoGame.playGame();
    }
}

