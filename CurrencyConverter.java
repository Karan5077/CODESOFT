import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import org.json.JSONObject;

class TrieNode {
    HashMap<String, Double> exchangeRates;
    HashMap<Character, TrieNode> children;

    public TrieNode() {
        this.exchangeRates = new HashMap<>();
        this.children = new HashMap<>();
    }
}

class CurrencyTrie {
    private TrieNode root;

    public CurrencyTrie() {
        root = new TrieNode();
    }

    public void insert(String baseCurrency, String targetCurrency, double rate) {
        TrieNode node = root;
        for (char c : baseCurrency.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.exchangeRates.put(targetCurrency, rate);
    }

    public Double getRate(String baseCurrency, String targetCurrency) {
        TrieNode node = root;
        for (char c : baseCurrency.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return null;
            }
            node = node.children.get(c);
        }
        return node.exchangeRates.getOrDefault(targetCurrency, null);
    }
}

public class CurrencyConverter {
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    private static CurrencyTrie trie = new CurrencyTrie();

    public static void fetchExchangeRates(String baseCurrency) throws IOException {
        String urlString = API_URL + baseCurrency;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder result = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                result.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject jsonResponse = new JSONObject(result.toString());
            JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");

            for (String targetCurrency : conversionRates.keySet()) {
                double rate = conversionRates.getDouble(targetCurrency);
                trie.insert(baseCurrency, targetCurrency, rate);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Base Currency (e.g., USD): ");
        String baseCurrency = scanner.next().toUpperCase();

        System.out.print("Enter Target Currency (e.g., INR): ");
        String targetCurrency = scanner.next().toUpperCase();

        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();

        try {
            Double exchangeRate = trie.getRate(baseCurrency, targetCurrency);

            if (exchangeRate == null) {
                System.out.println("Fetching exchange rates...");
                fetchExchangeRates(baseCurrency);
                exchangeRate = trie.getRate(baseCurrency, targetCurrency);
            }

            if (exchangeRate != null) {
                double convertedAmount = amount * exchangeRate;
                System.out.printf("Converted Amount: %.2f %s\n", convertedAmount, targetCurrency);
            } else {
                System.out.println("Exchange rate not available.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
