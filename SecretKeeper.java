import java.util.Random;

public class SecretKeeper extends Player {
    private Game game;
    private String secretCode;
    public int maxAttempts = 5;
    public int attemptsLeft = maxAttempts;

    
    public SecretKeeper(Game game, String[] args) {
        super("SecretKeeper");
        this.game = game; // Store referece to the Game instance
        if (hasSecretInArgs(args)) {
            secretCode = extractSecretFromArgs(args);
            maxAttempts = extractMaxAttemptsFromArgs(args);
        } else {
            secretCode = generateRandomSecret();
        }
    }

    private boolean hasSecretInArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
                // Search for input secret
            if (args[i].equals("-c") && i < args.length) {
                return true;
            }
        }
        return false;
    }

    public String extractSecretFromArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            // Search for input secret
            if (args[i].equals("-c") && i < args.length) {
                secretCode = args[i+1];
                i++;
            }
        }
        return secretCode;
    }
    
    private String generateRandomSecret() {
        Random num = new Random();
        StringBuilder secretBuilder = new StringBuilder();
        while (secretBuilder.length() < 4) {
            int int_random = num.nextInt(9);
            if (secretBuilder.indexOf(String.valueOf(int_random)) == -1) {
                secretBuilder.append(String.valueOf(int_random));
            }        
        }
        secretCode = secretBuilder.toString();
        // System.out.println(secret);
        return secretCode;
    }

    private int extractMaxAttemptsFromArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-t") && i < args.length) {
                try {
                    maxAttempts = Integer.parseInt(args[i+1]);
                    i++;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value for -t");
                    return 10;
                }
            }
        }
        return maxAttempts;
    }

    public String provideFeedback(String guess) {
    // Game logic; secretKeeper will determine well and misplaced
    // And return the feedback
    
        if (game.isValidGuess(guess)) {
            int wellPlaced = 0;
            int misPlaced = 0;

            for (int i = 0; i < 4; i++) {
                char secretChar = secretCode.charAt(i);
                char guessChar = guess.charAt(i);

                if (secretChar == guessChar) {
                    wellPlaced++;
                }
                else if (secretCode.indexOf(guessChar) != -1) {
                    misPlaced++;
                }
            }

            // If all pieces are well-placed, winner
            if (wellPlaced == 4) {
                System.out.println("Congrats! You did it!");
                System.exit(0);
            }
            
            String feedback = "Well placed pieces: " + wellPlaced + "\n";
            feedback += "Misplaced pieces: " + misPlaced + "\n";
            feedback += "---\n";
            attemptsLeft--;

            // Print when Round > 0
            if (attemptsLeft > 0) {
                System.out.println("Round " + (maxAttempts - attemptsLeft));
            }
            return feedback;
        }
        return "Invalid guess";
    }
    
}
    


