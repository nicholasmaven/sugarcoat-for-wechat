package com.github.nicholasmaven.sugarcoat.wechat;

/**
 * @author mawen
 * @date 2019-03-01 11:23
 */
public class DeserializeFeature {

    public static final DeserializeFeature NONE = new DeserializeFeature();
    /**
     * The first level element is isolated, for example:
     * <pre>
     * {
     *   "level1": {
     *     "prop1": "val1"
     *   }
     * }
     * </pre>
     * Here the "level1" is considered as isolated
     *
     * <p>NOTE: Array is not supported</p>
     */
    public static final DeserializeFeature FIRST_PLAIN_ISOLATED = new DeserializeFeature();

    public static ListCanBeFoldedFeature listCanBeFolded(String arrayName) {
        return new ListCanBeFoldedFeature(arrayName);
    }

    /**
     * Json property to Java List
     *
     * <pre>
     * {
     *     "prop_1": "value1",
     *     "prop_2": "value2",
     *     ...
     * }
     * =>
     * List<Item>
     * class Item {
     *     String prop;
     *     // getter and setter
     * }
     * </pre>
     */
    public static class ListCanBeFoldedFeature extends DeserializeFeature {
        private String arrayName;

        private ListCanBeFoldedFeature(String arrayName) {
            this.arrayName = arrayName;
        }

        public String getArrayName() {
            return arrayName;
        }
    }
}
