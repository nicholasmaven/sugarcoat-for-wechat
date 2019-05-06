package com.github.nicholasmaven.sugarcoat.wechat;

import org.springframework.util.Assert;

/**
 * @author mawen
 * @date 2019-03-04 17:50
 */
public class SerializeFeature {
    public static final SerializeFeature NONE = new SerializeFeature();

    public static CompletionFeature containerizeCompletion(String baseName, String propertyName) {
        return new ContainerizedCompletionFeature(baseName, propertyName);
    }

    public static CompletionFeature valueCompletion(String baseName, String propertyName) {
        return new ValueCompletionFeature(baseName, propertyName);
    }

    @Override
    public String toString() {
        return "vacant serialize feature";
    }

    /**
     * <pre>
     * class ObjectToJson{
     *     String prop = "value"
     *     // getter and setter
     * }
     * =>
     * {
     *     "nameYouDefined": {
     *         "prop": "value"
     *     }
     * }
     * </pre>
     */
    public static class CompletionFeature extends SerializeFeature {
        private String baseName;
        private String propertyName;

        private CompletionFeature(String baseName, String propertyName) {
            Assert.hasText(propertyName, "propertyName is null or empty");
            this.baseName = baseName;
            this.propertyName = propertyName;
        }

        public String getBaseName() {
            return baseName;
        }

        public String getPropertyName() {
            return propertyName;
        }

        @Override
        public String toString() {
            return "[baseName:" + baseName + "propertyName:" + propertyName + "]";
        }
    }

    /**
     * Object or Array
     */
    public static class ContainerizedCompletionFeature extends CompletionFeature {
        private ContainerizedCompletionFeature(String baseName, String propertyName) {
            super(baseName, propertyName);
        }
    }

    /**
     * <pre>
     * String prop="value"
     * =>
     * {
     *     "base_name": {
     *         "prop_name": "value"
     *     }
     * }
     * </pre>
     */
    public static class ValueCompletionFeature extends CompletionFeature {
        private ValueCompletionFeature(String baseName, String propertyName) {
            super(baseName, propertyName);
        }
    }
}
