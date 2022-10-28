package com.cl.learn.modifier.package1;

/**
 *
 */
public class ModifierDemo1_class {

    public String public_ =        "public_";
    protected String protected_ =  "protected_";
    protected static String protected_static =  "protected_static";
    String default_ =              "default_";
    private String private_ =      "private_";



    public static void main(String[] args) {
        ModifierDemo1_class modifierDemo1_class = new ModifierDemo1_class();
        System.out.println(modifierDemo1_class.public_);
        System.out.println(modifierDemo1_class.protected_);
        System.out.println(modifierDemo1_class.default_);
        System.out.println(modifierDemo1_class.protected_);
    }



}
