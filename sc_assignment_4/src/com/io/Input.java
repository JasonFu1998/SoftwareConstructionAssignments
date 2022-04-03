package com.io;

import exception.ReviseOrStopException;
import exception.YesOrNoException;
import exception.HitOrStandException;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Input {
    private final Scanner scanUserInput;

    public Input() {
        scanUserInput = new Scanner(System.in);
    }

    /**
     * @return the {@link com.players.Player}'s answer ('Y' or 'N') to whether he wants to play again or not
     * @throws YesOrNoException
     */
    public String askPlayerToPlayAgain() throws YesOrNoException {
        System.out.print("Would you like to play a new Round? Write 'Y' for YES and 'N' for NO:     ");
        String Input = scanUserInput.nextLine().toUpperCase(Locale.ROOT);
        if (Input.equals("Y") || Input.equals("N")) {
            return Input;
        }
        throw new YesOrNoException();
    }

    /**
     * @return the {@link com.players.Player}'s answer ('H' or 'S') to whether he wants to play again or not
     * @throws HitOrStandException
     */
    public String askPlayerHitOrStay() throws HitOrStandException {
        System.out.print("\nBased on the cards on display: Write 'H' to hit (and get another card) and 'S' to stand (stop this round):     ");
        String Input = scanUserInput.nextLine().toUpperCase(Locale.ROOT);
        if (Input.equals("H") || Input.equals("S")) {
            return Input;
        }
        throw new HitOrStandException();
    }

    /**
     * @return the {@link com.players.Player}'s input how much he wants to bet
     */
    public String askPlayerBetAmount() {
        return scanUserInput.nextLine();
    }

    /**
     * Asks the {@link com.players.Player} whether he wants to increase his balance. If yes, then the
     * {@link com.players.Player} will be asked how much he wants to deposit by invoking {@link #askDepositAmount()}
     *
     * @return 0 or the result of {@link #askDepositAmount()}
     */
    public int askForNewDeposit() {
        System.out.print("Would you like to increase your balance? Write 'Y' for YES and 'N' for NO:     ");
        String answer = scanUserInput.nextLine().toUpperCase(Locale.ROOT);
        if (answer.equals("Y")) {
            return askDepositAmount();
        } else if (answer.equals("N")) {
            return 0;
        }
        System.out.println("The input has to be an 'Y' or 'N' !!!");
        return askForNewDeposit();
    }

    /**
     * Asks the {@link com.players.Player} for his new deposit.
     *
     * @return the amount the {@link com.players.Player} wants to deposit as an integer
     */
    public int askDepositAmount() {
        int depositAmount;
        int minDeposit = 1;
        int maxDeposit = 1000;
        System.out.print("How much CHF would you like to deposit (" + minDeposit + " - " + maxDeposit + "):      ");
        try {
            depositAmount = parseInt(scanUserInput.nextLine());
            if (depositAmount > maxDeposit || depositAmount < minDeposit) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("The input has to be an integer between " + minDeposit + " and " + maxDeposit + "!!!");
            return askDepositAmount();
        }
        return depositAmount;
    }

    /**
     * Asks the {@link com.players.Player} whether he wants to revise his bet amount or stop the game.
     *
     * @return 'true' if he wants to revise his bet amount, 'false' if he wants to stop the game.
     * @throws ReviseOrStopException
     */
    public boolean askForDifferentBetAmount() throws ReviseOrStopException {
        System.out.println("\nIf you would like to revise your bet amount write 'R', otherwise write 'S' to stop playing completely:       ");
        String answer = scanUserInput.nextLine().toUpperCase(Locale.ROOT);
        if (answer.equals("R")) {
            return true;
        } else if (answer.equals("S")) {
            return false;
        }
        throw new ReviseOrStopException();
    }
}
