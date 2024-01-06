package baseClasses.Battle;
import baseClasses.User.User;
import baseClasses.Card.Card;

import java.util.List;
import java.util.Random;

public class Battle {

    private User player1;
    private User player2;
    private String battleLog;

    public Battle(User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;
        battleLog = "";
    }

    private int battleDecks() {

        Random random = new Random();

        List<Card> tempDeckPlayer1 = player1.getStack();
        List<Card> tempDeckPlayer2 = player2.getStack();

        Card card1;
        Card card2;

        for(int rounds = 1; rounds <= 100; rounds++) {
            //check if either player won
            if(tempDeckPlayer1.isEmpty()) return 2;
            if(tempDeckPlayer2.isEmpty()) return 1;

            card1 = tempDeckPlayer1.get(random.nextInt(tempDeckPlayer1.size()));
            card2 = tempDeckPlayer2.get(random.nextInt(tempDeckPlayer2.size()));

            this.battleLog += rounds + ". Round: " + player1.getUsername() + "s " + card1.getCardName() + " [" + card1.getCardElement() + "] ("
                + card1.getCardDamage() + ") VS " + player2.getUsername() + "s " + card2.getCardName() + " [" + card2.getCardElement() + "] ("
                + card2.getCardDamage() + ")\n";

            if(card1.beAttacked(card2)) {
                tempDeckPlayer1.remove(card1);
                tempDeckPlayer2.add(card1);
                this.battleLog += "=> " + card2.getCardName() + " wins!\n";
                continue;
            }


            if(card2.beAttacked(card1)) {
                tempDeckPlayer2.remove(card2);
                tempDeckPlayer1.add(card2);
                this.battleLog += "=> " + card1.getCardName() + " wins!\n";
                continue;
            }

            if(card1.getBattleDamage(card2) == card2.getBattleDamage(card1)) {
                this.battleLog += "=> draw!\n";
                continue;
            }

            if(card1.getBattleDamage(card2) > card2.getBattleDamage(card1)) {
                tempDeckPlayer2.remove(card2);
                tempDeckPlayer1.add(card2);
                this.battleLog += "=> " + card1.getBattleDamage(card2) + " VS " + card2.getBattleDamage(card1) + " -> " + card1.getCardName() + " wins!\n";
                continue;
            }

            tempDeckPlayer1.remove(card1);
            tempDeckPlayer2.add(card1);
            this.battleLog += "=> " + card1.getBattleDamage(card2) + " VS " + card2.getBattleDamage(card1) + " -> " + card2.getCardName() + " wins!\n";
        }
        //if 100 rounds have been played without a winner this function returns 0 for a draw
        return 0;
    }

    public void commenceBattle() {

        this.battleLog += "Battle: " + player1.getUsername() + " vs " + player2.getUsername() + "\n";

        int winner = battleDecks();

        if(winner == 1) {
            player1.wins();
            player2.loses();
            updateELOofUsers(winner);
            this.battleLog += player1.getUsername() + " wins! ELOs have been changed accordingly!";
        }
        if(winner == 2) {
            player2.wins();
            player1.loses();
            updateELOofUsers(winner);
            this.battleLog += player2.getUsername() + " wins! ELOs have been changed accordingly!";
        }

        if(winner == 0) {
                this.battleLog += "This battle ended in a Draw! ELOs have not been changed!";
        }

        System.out.print(this.battleLog);
    }

    private void updateELOofUsers(int winner) {

        if(winner == 1) {

            int eloChange = (int) Math.round(20 * (1 - calcWinProb(player2.getUserElo(), player1.getUserElo())));

            player1.updateUserElo(-eloChange);
            player2.updateUserElo(eloChange);
        }

        if(winner == 2) {

            int eloChange = (int) Math.round(20 * (1 - calcWinProb(player1.getUserElo(), player2.getUserElo())));

            player1.updateUserElo(-eloChange);
            player2.updateUserElo(eloChange);
        }
    }

    private double calcWinProb(int winnerElo, int loserElo) {

        return 1.0 / (1.0 + Math.pow(10.0, ((double) winnerElo - (double) loserElo) / 400));
    }

    public User returnPlayer1() {
        return this.player1;
    }

    public User returnPlayer2() {
        return this.player2;
    }

    public String getBattleLog() {
        return this.battleLog;
    }

    public void addUser1(User user) {this.player1 = user;}
    public void addUser2(User user) {this.player2 = user;}
}
