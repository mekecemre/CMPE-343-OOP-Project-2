package utils;

/**
 * Animation class providing ASCII art animations for system startup and shutdown.
 * Enhances user experience with visual feedback during system transitions.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class Animation {

    /**
     * Private constructor to prevent instantiation.
     */
    private Animation() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Displays the startup animation with system logo and loading effect.
     */
    public static void showStartupAnimation() {
        ColorUtils.clearScreen();

        String[] frames = {
            "    ╔═════════════════════════════════════════════════════════════════╗",
            "    ║                                                                 ║",
            "    ║   ██████╗ ██████╗ ███╗   ██╗████████╗ █████╗  ██████╗████████╗  ║",
            "    ║  ██╔════╝██╔═══██╗████╗  ██║╚══██╔══╝██╔══██╗██╔════╝╚══██╔══╝  ║",
            "    ║  ██║     ██║   ██║██╔██╗ ██║   ██║   ███████║██║        ██║     ║",
            "    ║  ██║     ██║   ██║██║╚██╗██║   ██║   ██╔══██║██║        ██║     ║",
            "    ║  ╚██████╗╚██████╔╝██║ ╚████║   ██║   ██║  ██║╚██████╗   ██║     ║",
            "    ║   ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝   ╚═╝     ║",
            "    ║                                                                 ║",
            "    ║              MANAGEMENT SYSTEM - CMPE 343 PROJECT               ║",
            "    ║                                                                 ║",
            "    ╚═════════════════════════════════════════════════════════════════╝",
        };

        // Display logo
        for (String frame : frames) {
            System.out.println(
                ColorUtils.colorize(frame, ColorUtils.BRIGHT_CYAN)
            );
        }

        System.out.println();
        System.out.print(ColorUtils.colorize("    Loading", ColorUtils.YELLOW));

        // Loading animation
        for (int i = 0; i < 15; i++) {
            try {
                Thread.sleep(100);
                System.out.print(ColorUtils.colorize(".", ColorUtils.YELLOW));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println(ColorUtils.colorize(" Done!", ColorUtils.GREEN));
        System.out.println();

        // Welcome message with animation
        String[] welcomeText = {
            "    ╔════════════════════════════════════════════════════════════════════════════╗",
            "    ║                               WELCOME TO THE                               ║",
            "    ║                    ROLE-BASED CONTACT MANAGEMENT SYSTEM                    ║",
            "    ║                                                                            ║",
            "    ║                     Secure • Efficient • User-Friendly                     ║",
            "    ╚════════════════════════════════════════════════════════════════════════════╝",
        };

        for (String line : welcomeText) {
            System.out.println(ColorUtils.colorize(line, ColorUtils.GREEN));
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "    System initialized successfully!",
                ColorUtils.BRIGHT_GREEN
            )
        );
        System.out.println();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Displays the shutdown animation when the system is closing.
     */
    public static void showShutdownAnimation() {
        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "    ╔═════════════════════════════════════════════════════════════════════════╗",
                ColorUtils.YELLOW
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "    ║                         SHUTTING DOWN SYSTEM...                         ║",
                ColorUtils.YELLOW
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "    ╚═════════════════════════════════════════════════════════════════════════╝",
                ColorUtils.YELLOW
            )
        );
        System.out.println();

        // Closing animation
        String[] shutdownFrames = {
            "    [████████████████████████████████████████] 100%",
            "    [████████████████████████████████████░░░░] 90%",
            "    [████████████████████████████████░░░░░░░░] 80%",
            "    [████████████████████████████░░░░░░░░░░░░] 70%",
            "    [████████████████████████░░░░░░░░░░░░░░░░] 60%",
            "    [████████████████████░░░░░░░░░░░░░░░░░░░░] 50%",
            "    [████████████████░░░░░░░░░░░░░░░░░░░░░░░░] 40%",
            "    [████████████░░░░░░░░░░░░░░░░░░░░░░░░░░░░] 30%",
            "    [████████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░] 20%",
            "    [████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░] 10%",
            "    [░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░] 0%",
        };

        for (int i = shutdownFrames.length - 1; i >= 0; i--) {
            System.out.print(
                "\r" + ColorUtils.colorize(shutdownFrames[i], ColorUtils.RED)
            );
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("\n");

        // Goodbye message
        String[] goodbyeArt = {
            "    ╔═══════════════════════════════════════════════════════════════════╗",
            "    ║                                                                   ║",
            "    ║            ████████╗██╗  ██╗ █████╗ ███╗   ██╗██╗  ██╗            ║",
            "    ║            ╚══██╔══╝██║  ██║██╔══██╗████╗  ██║██║ ██╔╝            ║",
            "    ║               ██║   ███████║███████║██╔██╗ ██║█████╔╝             ║",
            "    ║               ██║   ██╔══██║██╔══██║██║╚██╗██║██╔═██╗             ║",
            "    ║               ██║   ██║  ██║██║  ██║██║ ╚████║██║  ██╗            ║",
            "    ║               ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝            ║",
            "    ║                                                                   ║",
            "    ║                     YOU FOR USING OUR SYSTEM!                     ║",
            "    ║                                                                   ║",
            "    ║                        See you again soon!                        ║",
            "    ║                                                                   ║",
            "    ╚═══════════════════════════════════════════════════════════════════╝",
        };

        for (String line : goodbyeArt) {
            System.out.println(
                ColorUtils.colorize(line, ColorUtils.BRIGHT_MAGENTA)
            );
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "    System shutdown complete. Goodbye!",
                ColorUtils.GREEN
            )
        );
        System.out.println();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Displays a simple loading animation.
     *
     * @param message The message to display during loading
     * @param duration The duration of the loading animation in milliseconds
     */
    public static void showLoading(String message, int duration) {
        System.out.print(ColorUtils.colorize(message, ColorUtils.CYAN));
        int steps = duration / 200;
        for (int i = 0; i < steps; i++) {
            try {
                Thread.sleep(200);
                System.out.print(ColorUtils.colorize(".", ColorUtils.CYAN));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(ColorUtils.colorize(" Done!", ColorUtils.GREEN));
    }

    /**
     * Displays a progress bar animation.
     *
     * @param current The current progress value
     * @param total The total value (100%)
     * @param label The label to display with the progress bar
     */
    public static void showProgressBar(int current, int total, String label) {
        int percentage = (int) (((double) current / total) * 100);
        int filled = percentage / 2; // 50 character wide bar

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 50; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("] ").append(percentage).append("% - ").append(label);

        System.out.print(
            "\r" + ColorUtils.colorize(bar.toString(), ColorUtils.BRIGHT_BLUE)
        );

        if (current >= total) {
            System.out.println();
        }
    }

    /**
     * Displays a spinner animation for a specified duration.
     *
     * @param message The message to display with the spinner
     * @param durationMs The duration in milliseconds
     */
    public static void showSpinner(String message, int durationMs) {
        String[] spinner = { "|", "/", "-", "\\" };
        int iterations = durationMs / 100;

        for (int i = 0; i < iterations; i++) {
            System.out.print(
                "\r" +
                    ColorUtils.colorize(
                        message + " " + spinner[i % 4],
                        ColorUtils.CYAN
                    )
            );
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.print(
            "\r" + ColorUtils.colorize(message + " ✓", ColorUtils.GREEN)
        );
        System.out.println();
    }
}
