package com.example.demo.utils.bank;

import com.example.demo.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class BankAccountUtils {

    private static final SecureRandom random = new SecureRandom();
    private static final int ACCOUNT_NUMBER_LENGTH = 11;


    private final BankAccountRepository bankAccountRepository;

    public String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.valueOf((long) (Math.random() * 1_000_000_00000L));
            accountNumber = String.format("%011d", Long.parseLong(accountNumber));
        } while (bankAccountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    public List<String> suggestBeautifulNumbers(String prefix, int count) {
        List<String> suggestions = new ArrayList<>();
        Set<String> unique = new HashSet<>();

        while (unique.size() < count) {
            String number = generateBeautifulNumber(prefix);
            if (unique.add(number)) {
                suggestions.add(number);
            }
        }
        return suggestions;
    }

    private String generateBeautifulNumber(String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        while (sb.length() < ACCOUNT_NUMBER_LENGTH) {
            int pattern = random.nextInt(5);
            switch (pattern) {
                case 0 -> sb.append("0000");     // 0000
                case 1 -> sb.append("1111");     // 1111
                case 2 -> sb.append("6868");     // 6868
                case 3 -> sb.append("8888");     // 8888
                case 4 -> {
                    // Số lặp: 1212, 3434, ...
                    int digit = random.nextInt(10);
                    sb.append(digit).append(digit).append(digit).append(digit);
                }
            }
        }
        return sb.substring(0, ACCOUNT_NUMBER_LENGTH);
    }

    // 2. Tìm số gần giống (gần đúng)
    public List<String> searchSimilarNumbers(String input, List<String> existingNumbers, int limit) {
        return existingNumbers.stream()
                .filter(num -> isSimilar(input, num))
                .sorted((a, b) -> Integer.compare(distance(input, b), distance(input, a)))
                .limit(limit)
                .collect(Collectors.toList());
    }
    public String generateSimilarNumber(String input) {
        StringBuilder sb = new StringBuilder();

        // 1. Bắt đầu bằng input
        sb.append(input);

        // 2. Điền đủ 11 số
        while (sb.length() < ACCOUNT_NUMBER_LENGTH) {
            int choice = random.nextInt(3);
            switch (choice) {
                case 0 -> {
                    // Thêm số lặp (6868, 1111, 0000)
                    String repeat = switch (random.nextInt(4)) {
                        case 0 -> "0000";
                        case 1 -> "1111";
                        case 2 -> "6868";
                        case 3 -> "8888";
                        default -> "1212";
                    };
                    sb.append(repeat, 0, Math.min(4, ACCOUNT_NUMBER_LENGTH - sb.length()));
                }
                case 1 -> {
                    // Thêm số ngẫu nhiên
                    sb.append(random.nextInt(10));
                }
                case 2 -> {
                    // Thêm số từ input (nếu còn)
                    if (sb.length() < ACCOUNT_NUMBER_LENGTH) {
                        sb.append(input.charAt(random.nextInt(input.length())));
                    }
                }
            }
        }

        return sb.substring(0, ACCOUNT_NUMBER_LENGTH);
    }

    private boolean isSimilar(String input, String number) {
        if (input.length() > number.length()) return false;
        return number.startsWith(input) || number.contains(input);
    }

    private int distance(String input, String number) {
        return Math.abs(number.length() - input.length()) +
                (number.startsWith(input) ? 0 : 1);
    }

    // 3. Kiểm tra hợp lệ
    public boolean isValidFormat(String accountNumber) {
        return accountNumber != null &&
                accountNumber.matches("\\d{11}") &&
                accountNumber.length() == 11;
    }
}

