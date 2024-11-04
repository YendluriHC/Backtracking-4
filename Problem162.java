//TC: O(m^k log(m^k))
//SC: O(m^k)
class Solution {
    public String[] expand(String s) {
        // Parse the string into a list of options
        List<List<Character>> options = new ArrayList<>();
        int i = 0;
        
        while (i < s.length()) {
            List<Character> current = new ArrayList<>();
            
            if (s.charAt(i) == '{') {
                i++; // Move past the opening brace
                // Parse all characters between braces, delimited by commas
                while (s.charAt(i) != '}') {
                    if (s.charAt(i) != ',') {
                        current.add(s.charAt(i));
                    }
                    i++;
                }
                i++; // Move past the closing brace
            } else {
                // If it's not a brace, it's a fixed character
                current.add(s.charAt(i));
                i++;
            }
            
            options.add(current);
        }
        
        // List to store all the possible combinations
        List<String> result = new ArrayList<>();
        // Backtrack to generate all combinations
        backtrack(options, 0, new StringBuilder(), result);
        
        // Sort the result lexicographically
        Collections.sort(result);
        
        // Convert the list to an array and return
        return result.toArray(new String[0]);
    }
    
    // Backtracking function to generate combinations
    private void backtrack(List<List<Character>> options, int index, StringBuilder current, List<String> result) {
        // If we've processed all groups, add the result
        if (index == options.size()) {
            result.add(current.toString());
            return;
        }
        
        // For each option in the current group, append and recurse
        for (char c : options.get(index)) {
            current.append(c); // Choose the current option
            backtrack(options, index + 1, current, result); // Recurse to the next group
            current.deleteCharAt(current.length() - 1); // Backtrack (remove last char)
        }
    }
}
