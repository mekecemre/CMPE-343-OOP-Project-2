package utils;

/**
 * ColorUtils class providing ANSI color codes for terminal output.
 * Supports colorful terminal display with Turkish character encoding.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class ColorUtils {

    // ANSI color codes
    /** ANSI code to reset all colors and styles */
    public static final String RESET = "\u001B[0m";
    /** ANSI code for black text */
    public static final String BLACK = "\u001B[30m";
    /** ANSI code for red text */
    public static final String RED = "\u001B[31m";
    /** ANSI code for green text */
    public static final String GREEN = "\u001B[32m";
    /** ANSI code for yellow text */
    public static final String YELLOW = "\u001B[33m";
    /** ANSI code for blue text */
    public static final String BLUE = "\u001B[34m";
    /** ANSI code for magenta text */
    public static final String MAGENTA = "\u001B[35m";
    /** ANSI code for cyan text */
    public static final String CYAN = "\u001B[36m";
    /** ANSI code for white text */
    public static final String WHITE = "\u001B[37m";

    // Bright colors
    /** ANSI code for bright black text */
    public static final String BRIGHT_BLACK = "\u001B[90m";
    /** ANSI code for bright red text */
    public static final String BRIGHT_RED = "\u001B[91m";
    /** ANSI code for bright green text */
    public static final String BRIGHT_GREEN = "\u001B[92m";
    /** ANSI code for bright yellow text */
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    /** ANSI code for bright blue text */
    public static final String BRIGHT_BLUE = "\u001B[94m";
    /** ANSI code for bright magenta text */
    public static final String BRIGHT_MAGENTA = "\u001B[95m";
    /** ANSI code for bright cyan text */
    public static final String BRIGHT_CYAN = "\u001B[96m";
    /** ANSI code for bright white text */
    public static final String BRIGHT_WHITE = "\u001B[97m";

    // Background colors
    /** ANSI code for black background */
    public static final String BG_BLACK = "\u001B[40m";
    /** ANSI code for red background */
    public static final String BG_RED = "\u001B[41m";
    /** ANSI code for green background */
    public static final String BG_GREEN = "\u001B[42m";
    /** ANSI code for yellow background */
    public static final String BG_YELLOW = "\u001B[43m";
    /** ANSI code for blue background */
    public static final String BG_BLUE = "\u001B[44m";
    /** ANSI code for magenta background */
    public static final String BG_MAGENTA = "\u001B[45m";
    /** ANSI code for cyan background */
    public static final String BG_CYAN = "\u001B[46m";
    /** ANSI code for white background */
    public static final String BG_WHITE = "\u001B[47m";

    // Text styles
    /** ANSI code for bold text */
    public static final String BOLD = "\u001B[1m";
    /** ANSI code for underlined text */
    public static final String UNDERLINE = "\u001B[4m";
    /** ANSI code for reversed (inverted) colors */
    public static final String REVERSED = "\u001B[7m";

    /**
     * Private constructor to prevent instantiation.
     */
    private ColorUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Colorizes a string with the specified color.
     *
     * @param text The text to colorize
     * @param color The ANSI color code
     * @return Colorized string
     */
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }

    /**
     * Colorizes a string with color and style.
     *
     * @param text The text to colorize
     * @param color The ANSI color code
     * @param style The ANSI style code
     * @return Colorized and styled string
     */
    public static String colorize(String text, String color, String style) {
        return style + color + text + RESET;
    }

    /**
     * Creates a colored and styled text with background.
     *
     * @param text The text to colorize
     * @param color The ANSI color code
     * @param bgColor The ANSI background color code
     * @param style The ANSI style code
     * @return Colorized, styled string with background
     */
    public static String colorize(
        String text,
        String color,
        String bgColor,
        String style
    ) {
        return style + bgColor + color + text + RESET;
    }

    /**
     * Clears the terminal screen.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prints a horizontal line with specified character and color.
     *
     * @param length The length of the line
     * @param character The character to use
     * @param color The color of the line
     */
    public static void printLine(int length, char character, String color) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < length; i++) {
            line.append(character);
        }
        System.out.println(colorize(line.toString(), color));
    }

    /**
     * Prints a horizontal line with default settings (60 characters, '=', CYAN).
     */
    public static void printLine() {
        printLine(60, '=', CYAN);
    }

    /**
     * Prints a success message in green.
     *
     * @param message The success message
     */
    public static void printSuccess(String message) {
        System.out.println(colorize("✓ " + message, GREEN));
    }

    /**
     * Prints an error message in red.
     *
     * @param message The error message
     */
    public static void printError(String message) {
        System.out.println(colorize("✗ " + message, RED));
    }

    /**
     * Prints a warning message in yellow.
     *
     * @param message The warning message
     */
    public static void printWarning(String message) {
        System.out.println(colorize("⚠ " + message, YELLOW));
    }

    /**
     * Prints an info message in cyan.
     *
     * @param message The info message
     */
    public static void printInfo(String message) {
        System.out.println(colorize("ℹ " + message, CYAN));
    }

    /**
     * Creates a box around text.
     *
     * @param text The text to box
     * @param color The color of the box
     * @return Boxed text as string
     */
    public static String createBox(String text, String color) {
        int length = text.length() + 4;
        StringBuilder box = new StringBuilder();

        // Top border
        box.append("╔");
        for (int i = 0; i < length - 2; i++) {
            box.append("═");
        }
        box.append("╗\n");

        // Text line
        box.append("║ ").append(text).append(" ║\n");

        // Bottom border
        box.append("╚");
        for (int i = 0; i < length - 2; i++) {
            box.append("═");
        }
        box.append("╝");

        return colorize(box.toString(), color);
    }

    /**
     * Prints a header with centered text.
     *
     * @param text The header text
     * @param width The width of the header
     * @param color The color of the header
     */
    public static void printHeader(String text, int width, String color) {
        int padding = (width - text.length()) / 2;
        StringBuilder header = new StringBuilder();

        // Top border
        header.append("╔");
        for (int i = 0; i < width - 2; i++) {
            header.append("═");
        }
        header.append("╗\n");

        // Text line
        header.append("║");
        for (int i = 0; i < padding - 1; i++) {
            header.append(" ");
        }
        header.append(text);
        for (int i = 0; i < width - text.length() - padding - 1; i++) {
            header.append(" ");
        }
        header.append("║\n");

        // Bottom border
        header.append("╚");
        for (int i = 0; i < width - 2; i++) {
            header.append("═");
        }
        header.append("╝");

        System.out.println(colorize(header.toString(), color));
    }
}
