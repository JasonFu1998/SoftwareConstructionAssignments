package com.players;

import com.cards.Card;
import com.cards.Deck;
import com.hand.Hand;
import com.io.Input;
import exception.HitOrStandException;

public class Dealer {
    private final Hand hand;
    private final Input scanner;
    private final Player player;
    private int betAmount;
    private Deck deck;

    public Dealer(Input scanner, Player player) {
        this.hand = new Hand();
        this.scanner = scanner;
        this.player = player;
        this.betAmount = 0;
    }

    /**
     * @param deck
     * @param player
     */
    public void dealCards(Deck deck, Player player) {
        player.dealCardToPlayer(deck);
        dealCardToDealer(deck);
        player.dealCardToPlayer(deck);
        dealCardToDealer(deck);
    }

    private void dealCardToDealer(Deck deck) {
        hand.addCardToHand(deck.nextCard());
    }

    public void addOneCardToHand(Card card) {
        hand.addCardToHand(card);
    }

    /**
     * @param deck
     * @return 'false' if the player has a BlackJack, 'true' if {@link #isTotalPlayerValueGreaterThan21()} returns true,
     * otherwise the result of {@link #checkWinnerIfPlayerUnder21()}
     */
    public boolean dealerTakesOver(Deck deck) {
        this.deck = deck;
        int totalPlayerValue = player.totalCardValue();
        if (totalPlayerValue == 21 && this.totalCardValue() != 21) {
            System.out.println("WOW! You have a BlackJack!");
            return false;
        }

        this.hand.printOneDealerCard();
        player.showPlayerCards();

        while (askHitOrStay()) {
            player.dealCardToPlayer(deck);
            player.showPlayerCards();
            if (isTotalPlayerValueGreaterThan21()) {
                return true;
            }
        }
        return checkWinnerIfPlayerUnder21();
    }

    /**
     * @return 'true' if the player's hand is greater than 21, else 'false'
     */
    public boolean isTotalPlayerValueGreaterThan21() {
        int totalPlayerValue = player.totalCardValue();
        if (totalPlayerValue > 21) {
            System.out.println("Your total card value is too large: " + totalPlayerValue + " You lost this round! Better luck next time!" + "\n");
            return true;
        }
        return false;
    }

    /**
     *
     */
    public void roundPlayerLossReset() {
        player.removeMoney(betAmount);
        player.clearPlayerHand();
        removeDealerHand();
    }

    /**
     *
     */
    public void roundPlayerWinReset() {
        player.depositMoney(betAmount);
        player.clearPlayerHand();
        removeDealerHand();
    }

    /**
     *
     */
    public void roundDrawReset() {
        player.clearPlayerHand();
        removeDealerHand();
    }

    /**
     * @return 'true' if the dealer's hand is more than 17 and more than the player's hand, else
     * {@link #dealerIsUnder17AndHasToPlay()}
     */
    public boolean checkWinnerIfPlayerUnder21() {
        if (totalCardValue() > 17) {
            return totalCardValue() > player.totalCardValue();
        }
        return dealerIsUnder17AndHasToPlay();
    }

    /**
     * Draws cards to the dealer as long as the dealer's hand is less than 17
     *
     * @return 'false' if the dealer's hand is more than 21, else {@link #totalCardValue()} >
     * {@link Player#totalCardValue()}
     */
    public boolean dealerIsUnder17AndHasToPlay() {
        while (totalCardValue() < 17) {
            dealCardToDealer(deck);
        }
        if (totalCardValue() > 21) {
            return false;
        } else {
            return totalCardValue() > player.totalCardValue();
        }
    }

    /**
     * Asks for the bet amount and returns the value.
     * @return the bet amount
     */
    public int askForBet() {
        System.out.print("\nHow much would you like to bet for? You have " + player.getBalance() + " CHF available:    ");
        try {
            this.betAmount = Integer.parseInt(scanner.askPlayerBetAmount());
            if (betAmount < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("You have to input a positive integer!!!");
            return askForBet();
        }
        return betAmount;
    }

    /**
     * @return 'true' if the player wants to hit, 'false' if the player wants to stay
     */
    public boolean askHitOrStay() {
        boolean playerHitOrStay;
        try {
            playerHitOrStay = scanner.askPlayerHitOrStay().equals("H");
        } catch (HitOrStandException e) {
            System.out.println("The input has to be either a 'H' or 'S'!!!");
            return askHitOrStay();
        }
        return playerHitOrStay;
    }

    /**
     * @return the hand's total card value
     */
    public int totalCardValue() {
        return hand.calculateTotalHandValue();
    }

    /**
     *
     */
    public void removeDealerHand() {
        this.hand.clearHand();
    }
}
