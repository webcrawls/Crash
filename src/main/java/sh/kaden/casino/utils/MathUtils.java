package sh.kaden.casino.utils;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Utilities that might help with some math operations.
 */
public class MathUtils {

    /**
     * Checks if a hash is divisible.
     *
     * @param hash Hash as a {@link String}.
     * @param mod  number to check division.
     * @return true if divisible, false if not.
     * @apiNote I have no clue what this does, how it works, when I found it, where I got it from. Just found it lying around in some old code.
     */
    public static boolean hashDivisible(final @NonNull String hash, final int mod) {
        int val = 0;

        final int o = hash.length() % 4;

        for (int i = o > 0 ? o - 4 : 0; i < hash.length(); i += 4) {
            val = ((val << 16) + Integer.parseInt(hash.substring(i, i + 4), 16)) % mod;
        }

        return val == 0;
    }

    /**
     * Rounds a number to two decimal points using {@link Math#round(double)}.
     *
     * @param number number to round.
     * @return {@code number} rounded to two decimal places.
     */
    public static double roundToTwoDecimalPoints(final double number) {
        return Math.round(number * 100) / 100.0;
    }

}
