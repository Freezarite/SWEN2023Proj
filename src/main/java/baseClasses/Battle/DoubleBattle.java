package baseClasses.Battle;

import baseClasses.User.User;

public class DoubleBattle {
    private User player1;
    private User player2;
    private User player3;
    private User player4;
    private String battleLog;

    public DoubleBattle(User player1, User player2, User player3, User player4) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;

        this.battleLog = "";
    }

    private int battleDecks() {
        return 0;
    }

    public void commenceBattle() {
        this.battleLog += "Team-Battle: " + player1.getUsername() + " & " + player2.getUsername() + " vs " +
                player3.getUsername()+ " & " + player4.getUsername() + "\n";

        int winner = battleDecks();

        if(winner == 1) {
            player1.wins();
            player2.wins();
            player3.loses();
            player4.loses();
            updateELOofUsers(winner);
            this.battleLog += player1.getUsername() + " & " + player2.getUsername() + " wins! ELOs have been changed accordingly!";
        }
        if(winner == 2) {
            player2.loses();
            player1.loses();
            player3.wins();
            player4.wins();
            updateELOofUsers(winner);
            this.battleLog += player3.getUsername() + " & " + player4.getUsername() + " wins! ELOs have been changed accordingly!";
        }

        if(winner == 0) {
            this.battleLog += "This battle ended in a Draw! ELOs have not been changed!";
        }

        //TODO: Update DB based on results

        System.out.print(this.battleLog);
    }

    private void updateELOofUsers(int winner) {

        if(winner == 1) {

            int eloChange = (int) Math.round(20 * (1 - calcWinProb(getAverage(player1.getUserElo(), player2.getUserElo()),
                    getAverage(player3.getUserElo(), player4.getUserElo()))));

            player1.updateUserElo(eloChange);
            player2.updateUserElo(eloChange);
            player3.updateUserElo(-eloChange);
            player4.updateUserElo(-eloChange);
        }

        if(winner == 2) {

            int eloChange = (int) Math.round(20 * (1 - calcWinProb(getAverage(player3.getUserElo(), player4.getUserElo()),
                    getAverage(player1.getUserElo(), player2.getUserElo()))));

            player1.updateUserElo(-eloChange);
            player2.updateUserElo(-eloChange);
            player3.updateUserElo(eloChange);
            player4.updateUserElo(eloChange);
        }
    }

    private double calcWinProb(double winnerElo, double loserElo) {

        return 1.0 / (1.0 + Math.pow(10.0, (winnerElo - loserElo) / 400));
    }

    private double getAverage(int player1, int player2) {
        return ((double) player1 + (double) player2) / 2;
    }
}
