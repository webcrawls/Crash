package dev.kscott.casino.game.crash;

import com.google.common.hash.Hashing;
import dev.kscott.crash.utils.MathUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * Provides game-critical data including crash points.
 */
public class CrashProvider {

    private final @NonNull CrashConfig config;

    /**
     * The starting seed of the crash game.
     */
    private final @NonNull String gameSeed;

    /**
     * The current hash of the game.
     */
    private @NonNull String currentHash;

    /**
     * Constructs CrashProvider.
     *
     * @param config the {@link CrashConfig}.
     */
    @SuppressWarnings("UnstableApiUsage")
    public CrashProvider(final @NonNull CrashConfig config) {
        this.config = config;
        this.gameSeed = Hashing.sha256().hashString(config.getGameSeed(), StandardCharsets.UTF_8).toString();
        this.currentHash = generateHash();
    }

    /**
     * Generates a crash point.
     *
     * @return crash point as a {@code double}.
     */
    @SuppressWarnings("UnstableApiUsage")
    public double generateCrashPoint() {
        final @NonNull String hash = Hashing.hmacSha256(generateHash().getBytes(StandardCharsets.UTF_8))
                .hashString(gameSeed, StandardCharsets.UTF_8).toString();

        // 1/51 games will auto crash at 1.
        if (MathUtils.hashDivisible(hash, 51)) {
            return 1;
        }

        final long h = Long.parseLong(hash.substring(0, 52 / 4).toUpperCase(), 16);

        final long e = (long) Math.pow(2, 52);

        final double crashPoint =  BigDecimal.valueOf(Math.floor((100.0 * e - h) / (e - h)) / 100).doubleValue();

        if (config.getMaxCrashPoint() != -1) {
            return Math.min(crashPoint, config.getMaxCrashPoint());
        } else {
            return crashPoint;
        }
    }

    /**
     * Generates a random hash based on the previous hash.
     *
     * @return hash as {@link String}.
     */
    @SuppressWarnings("UnstableApiUsage")
    private String generateHash() {
        currentHash = Hashing.sha256().hashString(
                currentHash == null ? Double.toString(Math.random() * 1000) : currentHash, StandardCharsets.UTF_8
        ).toString();
        return currentHash;
    }

}
