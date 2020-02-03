
package aquarius0715.slot_calc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class Slot_calc extends JavaPlugin implements CommandExecutor {

    /////////////
    // 変数作成 //
    /////////////

    long default_Stock;    //初期ストック
    long add_Stock;        //一回転で増えるストック
    long bet;              //一回の金額
    long number_Of_Times;  //回数
    double time = 0.0;     //一回転の時間
    double reduction_Rate; //還元率  ％表記に使う

    //計算専用
    long stock_Calc;                //add_Stockを足したストック
    long bet_Calc;                  //一回の金額
    double time_Calc;                 //一回転の時間
    long number_Of_Times_calc = 0;  //回数


    @Override
    public void onEnable() {
        this.getCommand("slotcalc").setExecutor(this);

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("slotcalc")) {
            if (!(sender instanceof Player)) {
                Player player = (Player) sender;
                sender.sendMessage("You cannot this");
                return true;
            }
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage("========================================SlotCalcPlugin========================================");
            sender.sendMessage("/slotcalc <初期ストック> <一回転で増えるストック> <一回転の金額> <一回転の時間(少数で入力)> <回す回数>");
            sender.sendMessage("========================================SlotCalcPlugin========================================");
            return true;
        }
        if (args.length == 5) {

            default_Stock = Long.parseLong((args[0]));
            add_Stock = Long.parseLong((args[1]));
            bet = Long.parseLong((args[2]));
            time = Double.parseDouble((args[3]));
            number_Of_Times = Long.parseLong((args[4]));
            ///計算

            for (int i = 0; i < number_Of_Times; i++) {
                ++number_Of_Times_calc; //回転数を増やす (1回 2回 3回...)

                stock_Calc = default_Stock + add_Stock * number_Of_Times_calc; //受け取るお金を制作 初期ストック + 追加ストック × 回数
                bet_Calc = bet * number_Of_Times_calc; //使ったお金を制作 一回転の金額 × 回数
                time_Calc = time * number_Of_Times_calc; //かかった時間を制作 一回転の時間 × 回数

                reduction_Rate = (stock_Calc + 0.0) / (bet_Calc + 0.0); //還元率を計算 受け取るお金 ÷ 使ったお金
                reduction_Rate *= 100.0; //％表記にするため100倍に

                sender.sendMessage(number_Of_Times_calc + "回転で当選した場合 還元率は " + reduction_Rate + " ％で、");
                sender.sendMessage("掛かる時間は " + time_Calc + " 秒です");
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return true;
                }
            }
        }
        return false;
    }
}
