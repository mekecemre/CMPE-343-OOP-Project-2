package models;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchCriteria class for building flexible, composable search queries.
 * Supports both exact and partial (substring-based) matches across multiple fields.
 * Demonstrates the Builder pattern and encapsulation principles.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class SearchCriteria {
    private List<Criterion> criteria;
    private String logicalOperator; // "AND" or "OR"

    /**
     * Inner class representing a single search criterion.
     */
    public static class Criterion {
        private String fieldName;
        private String value;
        private MatchType matchType;

        /**
         * Constructor for Criterion.
         *
         * @param fieldName The name of the field to search
         * @param value     The value to search for
         * @param matchType The type of match (EXACT or PARTIAL)
         */
        public Criterion(String fieldName, String value, MatchType matchType) {
            this.fieldName = fieldName;
            this.value = value;
            this.matchType = matchType;
        }

        /**
         * Gets the field name.
         *
         * @return The field name
         */
        public String getFieldName() {
            return fieldName;
        }

        /**
         * Gets the search value.
         *
         * @return The search value
         */
        public String getValue() {
            return value;
        }

        /**
         * Gets the match type.
         *
         * @return The match type
         */
        public MatchType getMatchType() {
            return matchType;
        }

        @Override
        public String toString() {
            return fieldName + " " + matchType + " '" + value + "'";
        }
    }

    /**
     * Enum representing the type of match for a search criterion.
     */
    public enum MatchType {
        /**
         * Exact match - field must exactly equal the value.
         */
        EXACT,

        /**
         * Partial match - field must contain the value as a substring.
         */
        PARTIAL
    }

    /**
     * Default constructor initializing with AND operator.
     */
    public SearchCriteria() {
        this.criteria = new ArrayList<>();
        this.logicalOperator = "AND";
    }

    /**
     * Constructor with specified logical operator.
     *
     * @param logicalOperator The logical operator to use ("AND" or "OR")
     */
    public SearchCriteria(String logicalOperator) {
        this.criteria = new ArrayList<>();
        this.logicalOperator = logicalOperator.toUpperCase();
    }

    /**
     * Adds a search criterion.
     *
     * @param fieldName The field name to search
     * @param value     The value to search for
     * @param matchType The type of match (EXACT or PARTIAL)
     * @return This SearchCriteria object for method chaining
     */
    public SearchCriteria addCriterion(String fieldName, String value, MatchType matchType) {
        criteria.add(new Criterion(fieldName, value, matchType));
        return this;
    }

    /**
     * Adds an exact match criterion.
     *
     * @param fieldName The field name to search
     * @param value     The exact value to match
     * @return This SearchCriteria object for method chaining
     */
    public SearchCriteria addExactMatch(String fieldName, String value) {
        return addCriterion(fieldName, value, MatchType.EXACT);
    }

    /**
     * Adds a partial match (substring) criterion.
     *
     * @param fieldName The field name to search
     * @param value     The substring to search for
     * @return This SearchCriteria object for method chaining
     */
    public SearchCriteria addPartialMatch(String fieldName, String value) {
        return addCriterion(fieldName, value, MatchType.PARTIAL);
    }

    /**
     * Gets the list of all criteria.
     *
     * @return List of criteria
     */
    public List<Criterion> getCriteria() {
        return new ArrayList<>(criteria);
    }

    /**
     * Gets the logical operator.
     *
     * @return The logical operator ("AND" or "OR")
     */
    public String getLogicalOperator() {
        return logicalOperator;
    }

    /**
     * Sets the logical operator.
     *
     * @param logicalOperator The logical operator to set ("AND" or "OR")
     */
    public void setLogicalOperator(String logicalOperator) {
        this.logicalOperator = logicalOperator.toUpperCase();
    }

    /**
     * Checks if there are any criteria defined.
     *
     * @return true if criteria list is not empty, false otherwise
     */
    public boolean hasCriteria() {
        return !criteria.isEmpty();
    }

    /**
     * Gets the number of criteria.
     *
     * @return The count of criteria
     */
    public int getCriteriaCount() {
        return criteria.size();
    }

    /**
     * Clears all criteria.
     */
    public void clear() {
        criteria.clear();
    }

    /**
     * Builds a SQL WHERE clause from the criteria.
     *
     * @return SQL WHERE clause string
     */
    public String toSQLWhereClause() {
        if (criteria.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < criteria.size(); i++) {
            Criterion criterion = criteria.get(i);

            if (i > 0) {
                sql.append(" ").append(logicalOperator).append(" ");
            }

            if (criterion.getMatchType() == MatchType.EXACT) {
                sql.append(criterion.getFieldName()).append(" = ?");
            } else {
                sql.append(criterion.getFieldName()).append(" LIKE ?");
            }
        }

        return sql.toString();
    }

    /**
     * Gets the values for prepared statement parameters.
     * For PARTIAL matches, adds wildcards.
     *
     * @return List of values for SQL parameters
     */
    public List<String> getParameterValues() {
        List<String> values = new ArrayList<>();
        for (Criterion criterion : criteria) {
            if (criterion.getMatchType() == MatchType.PARTIAL) {
                values.add("%" + criterion.getValue() + "%");
            } else {
                values.add(criterion.getValue());
            }
        }
        return values;
    }

    @Override
    public String toString() {
        if (criteria.isEmpty()) {
            return "No search criteria";
        }

        StringBuilder sb = new StringBuilder("Search: ");
        for (int i = 0; i < criteria.size(); i++) {
            if (i > 0) {
                sb.append(" ").append(logicalOperator).append(" ");
            }
            sb.append(criteria.get(i).toString());
        }
        return sb.toString();
    }
}
