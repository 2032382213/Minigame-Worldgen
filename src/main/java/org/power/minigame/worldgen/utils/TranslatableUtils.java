package org.power.minigame.worldgen.utils;

import net.minecraft.text.Text;

public class TranslatableUtils {
    public static Text getByKey(String key, Object... args) {
        String string = Text.translatable(key).getString();
        for(int i=0;i<=args.length-1;i++){
            string = string.replace("{"+ i +"}", String.valueOf(args[i]));
        }
        return Text.literal(string);
    }
}
