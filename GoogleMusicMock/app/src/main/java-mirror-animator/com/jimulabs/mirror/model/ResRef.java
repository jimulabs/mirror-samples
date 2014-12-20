package com.jimulabs.mirror.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lintonye on 2013-07-12.
 */
public class ResRef {

    // Matches a resource without a type qualifier.
    // Ex: "hello_world" or "Theme.Holo.Dark"
    private static final Pattern SHORT_REF_REGEX = Pattern.compile("(\\w+(\\.\\w+)*)");

    // Matches a resource with a type qualifier, and with an optional android prefix
    // Ex: "@string/hello_world" or "@android:color/white"
    private static final Pattern FULL_REF_REGEX = Pattern.compile(String.format("@(android:)?(\\w+)/(%s)", SHORT_REF_REGEX));
    /**
     * regex to parse ids such as: @+id/foo, @id/android:empty
     */
    private static final Pattern FULL_REF_REGEX2 = Pattern.compile(String.format("@\\+?(\\w+)/(android:)?(%s)", SHORT_REF_REGEX));

    // Matches a references to a resource on the file system
    // Ex: "images/icon1.png"
    private static final Pattern EXTERNAL_PATH_REGEX = Pattern.compile("(\\w+(/|\\\\))*\\w+(\\.\\w+)+");
    public final boolean isAndroidInternal;
    public final Type type;
    public final String name;
    public final boolean isValid;
    private String sourceFileName;
    private int sourceLineNumber;
    public ResRef(String name, Type type, boolean isAndroidInternal) {
        this(name, type, isAndroidInternal, true);
    }

    private ResRef(String name, Type type, boolean isAndroidInternal, boolean isValid) {
        this.isAndroidInternal = isAndroidInternal;
        this.name = name;
        this.type = type;
        this.isValid = isValid;
    }

    public static ResRef invalidRef(String content) {
        return new ResRef(content, Type.Unknown, false, false);
    }

    public static ResRef parseAnyRefOrThrow(String refStr, Type expectedType) {
        ResRef ref = parseAnyRef(refStr, expectedType);
        validateRef(ref, refStr);
        return ref;
    }

    public static ResRef parseAnyRef(String refStr, Type expectedType) {
        ResRef ref = parseFullRef(refStr);
        if (isValidRefForType(ref, expectedType)) {
            return ref;
        }

        ref = parseFileRef(refStr);
        if (isValidRefForType(ref, expectedType)) {
            return ref;
        }

        ref = parseUnqualifiedRef(refStr, expectedType);
        if (ref.isValid) {
            return ref;
        }

        return invalidRef(refStr);
    }

    private static boolean isValidRefForType(ResRef ref, Type expectedType) {
        return ref.isValid && typesMatch(expectedType, ref.type);
    }

    public static ResRef parseResourceRefOrThrow(String refStr, Type expectedType) {
        ResRef ref = parseResourceRef(refStr, expectedType);
        validateRef(ref, refStr);
        return ref;
    }

    public static ResRef parseResourceRef(String refStr, Type expectedType) {
        ResRef ref = parseFullRef(refStr);
        if (isValidRefForType(ref, expectedType)) {
            return ref;
        }

        ref = parseUnqualifiedRef(refStr, expectedType);
        if (ref.isValid) {
            return ref;
        }

        return invalidRef(refStr);
    }

    public static ResRef parseFullRefOrThrow(String refStr) {
        ResRef ref = parseFullRef(refStr);
        validateRef(ref, refStr);
        return ref;
    }

    public static ResRef parseFullRef(String ref) {
        ResRef resRef = parseFullRef(ref, FULL_REF_REGEX, 1, 2, 3);
        if (!resRef.isValid) {
            resRef = parseFullRef(ref, FULL_REF_REGEX2, 2, 1, 3);
        }
        return resRef;
    }

    private static ResRef parseFullRef(String ref, Pattern regex, int androidPrefixIndex, int typeIndex, int nameIndex) {
        Matcher m = regex.matcher(ref);
        if (m.matches()) {
            boolean internal = "android:".equals(m.group(androidPrefixIndex));
            Type type = Type.fromString(m.group(typeIndex));
            String name = m.group(nameIndex);
            return new ResRef(name, type, internal);
        } else {
            return invalidRef(ref);
        }
    }

    private static ResRef parseFileRef(String ref) {
        Matcher m = EXTERNAL_PATH_REGEX.matcher(ref);
        if (m.matches()) {
            return new ResRef(ref, Type.DrawableFile, false);
        } else {
            return invalidRef(ref);
        }
    }

    private static ResRef parseUnqualifiedRef(String ref, Type type) {
        Matcher m = SHORT_REF_REGEX.matcher(ref);
        if (m.matches()) {
            return new ResRef(m.group(1), type, false);
        } else {
            return invalidRef(ref);
        }
    }

    // A URL ref could be a local file or a webpage.
    // This is decided at run-time in WebViewPopulateCommand.
    public static ResRef parseUrlRef(String ref) {
        return new ResRef(ref, Type.html, false);
    }

    private static void validateRef(ResRef ref, String refStr) {
        if (!ref.isValid) {
            throw new IllegalArgumentException("Invalid reference: " + refStr);
        }
    }

    /**
     * There are a couple of special rules for matching:
     * <p/>
     * 1. Unknown matches anything
     * 2. DrawableFile and drawable can be used interchangably
     *
     * @return Whether the types are consistent with each other
     */
    private static boolean typesMatch(Type expected, Type actual) {
        if (expected.equals(Type.Unknown)) {
            return true;
        } else if (expected.equals(Type.DrawableFile)) {
            return actual.equals(Type.DrawableFile) || actual.equals(Type.drawable);
        } else if (expected.equals(Type.drawable)) {
            return actual.equals(Type.DrawableFile) || actual.equals(Type.drawable);
        } else {
            return expected.equals(actual);
        }
    }

    public boolean isExternalPath() {
        return Type.DrawableFile.equals(this.type);
    }

    public void setSource(String file, int line) {
        sourceFileName = file;
        sourceLineNumber = line;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public int getSourceLineNumber() {
        return sourceLineNumber;
    }

    @Override
    public String toString() {
        String android = isAndroidInternal ? "android:" : "";
        return String.format("@%s%s/%s", android, type, name);
    }

    public enum Type {
        style, layout, menu, color, string, drawable, DrawableFile, id, Unknown, html;

        public static Type fromString(String str) {
            try {
                return Type.valueOf(str);
            } catch (IllegalArgumentException e) {
                return Type.Unknown;
            }
        }
    }
}
