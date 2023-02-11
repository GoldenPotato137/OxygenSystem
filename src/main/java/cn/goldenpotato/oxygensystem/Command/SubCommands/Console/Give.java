package cn.goldenpotato.oxygensystem.Command.SubCommands.Console;

import cn.goldenpotato.oxygensystem.Command.SubCommandConsole;
import cn.goldenpotato.oxygensystem.Config.MessageManager;
import cn.goldenpotato.oxygensystem.Item.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Give extends SubCommandConsole {

    Map<String, ItemStack> items = new HashMap<>();
    public Give() {
        name = "give";
        permission = "oxygen.admin";
        usage = MessageManager.msg.SubCommand_Give_Usage;
        items.put("RoomDetector", RoomDetector.GetItem());
        items.put("BootStone", BootStone.GetItem());
        items.put("OxygenTank", OxygenTank.GetItem());
        items.put("OxygenTankProembryo", OxygenTankProembryo.GetItem());
        items.put("MaskUpgradeT1", MaskUpgradeT1.GetItem());
        items.put("MaskUpgradeT2", MaskUpgradeT2.GetItem());
        items.put("MaskUpgradeT3", MaskUpgradeT3.GetItem());
        items.put("OxygenGenerator", OxygenGenerator.GetItem());
        items.put("OxygenStation", OxygenStation.GetItem());
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(usage);
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) return;
        if (args.length == 1) {
            List<ItemStack> list = new ArrayList<>(items.values());
            list.forEach(itemStack -> itemStack.setAmount(1));
            list.forEach(target.getInventory()::addItem);
            return;
        }
        if (!items.containsKey(args[1])) {
            if (isInteger(args[1])){
                List<ItemStack> list = new ArrayList<>(items.values());
                int amount = Integer.parseInt(args[1]);
                if (amount != 1 && amount > 0) list.forEach(item -> item.setAmount(Integer.parseInt(args[1])));
                list.forEach(target.getInventory()::addItem);
                return;
            }
        }
        int amount = 1;
        if (args.length >= 3) {
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(MessageManager.msg.WrongNum);
                return;
            }
        }
        ItemStack item = items.get(args[1]);
        item.setAmount(amount);
        target.getInventory().addItem(item);
    }

    @Override
    public List<String> onTab(CommandSender sender, String[] args) {
        if (args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        if (args.length == 2) return new ArrayList<>(items.keySet());
        if (args.length == 3) return Collections.singletonList("1");
        return null;
    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
