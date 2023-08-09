package com.example.refactoringtool.codeduplication;

import org.eclipse.jface.text.IDocument;

import java.util.HashMap;
import java.util.Map;

public class CodeDuplication {

    public static boolean checkForCodeDuplication(IDocument doc, String selectedText) {
        String code = doc.get();
        int duplicationThreshold = selectedText.length(); // Set the threshold to the length of the selected code

        Map<String, Integer> duplicatedCodeMap = findDuplicatedCode(code, duplicationThreshold);

        for (Map.Entry<String, Integer> entry : duplicatedCodeMap.entrySet()) {
            String duplicatedCode = entry.getKey();
            if (duplicatedCode.equals(selectedText)) {
                // Found a duplication of the selected code
                return true;
            }
        }

        return false;
    }

    public static Map<String, Integer> findDuplicatedCode(String code, int duplicationThreshold) {
        Map<String, Integer> duplicatedCodeMap = new HashMap<>();
        int codeLength = code.length();

        for (int i = 0; i < codeLength; i++) {
            for (int j = i + duplicationThreshold; j < codeLength; j++) {
                String subCode = code.substring(i, j);
                int occurrenceCount = countOccurrences(code, subCode);

                if (occurrenceCount > 1 && !duplicatedCodeMap.containsKey(subCode)) {
                    duplicatedCodeMap.put(subCode, occurrenceCount);
                }
            }
        }

        return duplicatedCodeMap;
    }


    private static int countOccurrences(String code, String subCode) {
        int count = 0;
        int index = code.indexOf(subCode);

        while (index != -1) {
            count++;
            index = code.indexOf(subCode, index + subCode.length());
        }

        return count;
    }
}
