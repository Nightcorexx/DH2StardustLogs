import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final int[] heat = new int[7];  //contains heat in following order: log, oakLog, willowLog, mapleLog, sdLog, strangeLog, ancientLog

    static {
        heat[0] = 1;
        heat[1] = 2;
        heat[2] = 5;
        heat[3] = 10;
        heat[4] = 20;
        heat[5] = 30;
        heat[6] = 50;
    }

    public static void main(String[] args) throws IOException {
        final int[] prices = new int[8];  //contains in following order: log, oakLog, willowLog, mapleLog, sdLog, strangeLog, ancientLog, sd

        final String SELL = "Sell the stardust logs", USE = "Use the stardust logs";
        final int minRevenue, averageRevenue, maxRevenue;

        System.out.print("Enter the prices of following products in accurate order separated by a comma (type \"0\" " +
                "if none are available on the market or you don't know the market price):" +
                "\nNote that you have to enter a value for the sdLog and at least one different type of wood\n" +
                "log, oakLog, willowLog, mapleLog, sdLog, strangeLog, ancientLog, sd\n> ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final String[] line = br.readLine().replaceAll("\\s", "").split(",");
        if(line.length != 8) throw new IllegalStateException("You either entered too many or too few inputs");

        for (int i = 0; i < 8; i++) {
            prices[i] = Integer.parseInt(line[i]);
        }
        final int sdLogPrice = prices[4];  //just a quick shortcut
        final int sdLogHeat = heat[4];  //just a quick shortcut
        final int sdPrice = prices[7];  //just a quick shortcut

        Boolean wc = null;
        System.out.print("Type \"true\" or \"1\" if you have already unlocked Woodcutting hard perk and \"false\" or \"0\" if you haven't\n> ");

        boolean first = true;
        do {
            if(!first)
                System.out.print("Wrong input. Try again please\n> ");
            else
                first = false;

            String wcRead = br.readLine();
            if(wcRead.equalsIgnoreCase("true") || wcRead.equalsIgnoreCase("1"))
                wc = Boolean.TRUE;
            else if(wcRead.equalsIgnoreCase("false") || wcRead.equalsIgnoreCase("0"))
                wc = Boolean.FALSE;


        } while (wc == null);

        minRevenue = wc ? (int) (2000 * 1.25) : 2000;
        averageRevenue = wc ? (int) (6000 * 1.25) : 6000;
        maxRevenue = wc ? (int) (10_000 * 1.25) : 10_000;

        double bestPricePerHeat = Double.MAX_VALUE;
        int logIndex = -1;

        for (int i = 0; i < 7; i++) {
            if((prices[i] != 0) && ((double) prices[i] / heat[i] < bestPricePerHeat)) {
                bestPricePerHeat = (double) prices[i] / heat[i];
                logIndex = i;
            }
        }

        final String bestLog;

        switch (logIndex) {
            case 0:
                bestLog = "log";
                break;
            case 1:
                bestLog = "oak logs";
                break;
            case 2:
                bestLog = "willow logs";
                break;
            case 3:
                bestLog = "maple logs";
                break;
            case 4:
                bestLog = "stardust logs";
                break;
            case 5:
                bestLog = "strange logs";
                break;
            case 6:
                bestLog = "ancient logs";
                break;
            default:
                throw new IllegalStateException("Some inputs were either missing or incorrect");
        }

        //heat revenue
        final int sdLogHeatPrice = (int) (bestPricePerHeat * sdLogHeat);

        //sd revenue
        final int minSdLogSdRev = minRevenue * sdPrice;
        final int averageSdLogSdRev = averageRevenue * sdPrice;
        final int maxSdLogSdRev = maxRevenue * sdPrice;

        //heat and sd revenue
        final int minSdLogPrice = minSdLogSdRev + sdLogHeatPrice;
        final int averageSdLogPrice = averageSdLogSdRev + sdLogHeatPrice;
        final int maxSdLogPrice = maxSdLogSdRev + sdLogHeatPrice;

        System.out.print("Please enter how many stardust logs you own\n> ");
        int amount = Integer.parseInt(br.readLine());

        final long safeSell = amount * sdLogPrice;

        //revenue in coins (in both heat and sd)
        final long minRev = amount * (minSdLogPrice - sdLogPrice);
        final long avgRev = amount * (averageSdLogPrice - sdLogPrice);
        final long maxRev = amount * (maxSdLogPrice - sdLogPrice);
        System.out.println("---------------------\n" +
                "Best log in terms of heat per price and used for those calculations here are the " + bestLog + "\n" +
                "The following is the theoeretical revenue in coins (in both heat and stardust) from burning your stardust logs per log:\n" +
                "Minimum revenue from using your logs: " + minSdLogPrice + "$\n" +
                "\tYour profit would be: " + minRev + "$\n" +
                "Average revenue from using your logs: " + averageSdLogPrice + "$\n" +
                "\tYour profit would be: " + avgRev + "$\n" +
                "Maximum revenue from using your logs: " + maxSdLogPrice + "$\n" +
                "\tYour profit would be: " + maxRev + "$");

        final double minSdForWorth = ((double) (sdLogPrice - sdLogHeatPrice) / sdPrice) + 1;
        final int gapFlat = 8_000;
        final double minSdForWorthFlattened = wc ? minSdForWorth / 1.25 - 2000 : minSdForWorth - 2000;
        double riskL = (minSdForWorthFlattened / gapFlat) * 100;
        final int riskPTrunc = (int) (riskL * 100);  //truncate to 2 decimal places
        riskL = (double) riskPTrunc / 100;  //truncate to 2 decimal places
        final double riskP = 100 - riskL;

        final long worstLoss = safeSell - amount * (long) minSdLogPrice;
        final long bestWin = amount * (long) maxSdLogPrice - safeSell;

        System.out.println("\nBurning all logs at once leaves you with a chance of " + riskP + "% to make profit and a risk of " + riskL + "% to loose money\n" +
                "In worst case you loose " + worstLoss + "$ and in best case you gain " + bestWin + "$");

        br.close();
    }
}
