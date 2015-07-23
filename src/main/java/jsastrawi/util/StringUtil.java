/**
 * JSastrawi is licensed under The MIT License (MIT)
 *
 * Copyright (c) 2015 Andy Librian
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package jsastrawi.util;

public final class StringUtil {

    public static boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n';
    }

    public static boolean isWhitespace(String s) {
        return " ".equals(s) || "\t".equals(s) || "\n".equals(s);
    }

    public static int getNextWhitespace(String s, int start) {
        int i = start;

        while ((i < s.length() - 1) && !isWhitespace(s.charAt(i + 1))) {
            i++;
        }

        if (i == s.length() - 1) {
            throw new IndexOutOfBoundsException();
        }

        i++;

        return i;
    }

    public static int getNextWhitespace(String s) {
        return getNextWhitespace(s, -1);
    }

    public static boolean hasNextWhitespace(String s, int start) {
        int i = start;

        while ((i < s.length() - 1) && !isWhitespace(s.charAt(i + 1))) {
            i++;
        }

        if (i == s.length() - 1) {
            return false;
        }

        i++;

        return true;
    }

    public static boolean hasNextWhitespace(String s) {
        return hasNextWhitespace(s, -1);
    }

    public static int getPrevWhitespace(String s, int start) {
        int i = start;

        while (i > 0 && !isWhitespace(s.charAt(i - 1))) {
            i--;
        }

        if (i == 0) {
            throw new IndexOutOfBoundsException();
        }

        i--;

        return i;
    }

    public static int getPrevWhitespace(String s) {
        return getPrevWhitespace(s, s.length());
    }

    public static boolean hasPrevWhitespace(String s, int start) {
        int i = start;

        while (i > 0 && !isWhitespace(s.charAt(i - 1))) {
            i--;
        }

        if (i == 0) {
            return false;
        }

        return true;
    }

    public static boolean hasPrevWhitespace(String s) {
        return hasPrevWhitespace(s, s.length());
    }

    public static int getNextNonWhitespace(String s, int start) {
        int i = start;

        while ((i < s.length() - 1) && isWhitespace(s.charAt(i + 1))) {
            i++;
        }

        if (i == s.length() - 1) {
            throw new IndexOutOfBoundsException();
        }

        i++;

        return i;
    }

    public static int getNextNonWhitespace(String s) {
        return getNextNonWhitespace(s, -1);
    }

    public static boolean hasNextNonWhitespace(String s, int start) {
        int i = start;

        while ((i < s.length() - 1) && isWhitespace(s.charAt(i + 1))) {
            i++;
        }

        if (i == s.length() - 1) {
            return false;
        }

        i++;

        return true;
    }

    public static boolean hasNextNonWhitespace(String s) {
        return hasNextNonWhitespace(s, -1);
    }

    public static int getPrevNonWhitespace(String s, int start) {
        int i = start;

        while (i > 0 && isWhitespace(s.charAt(i - 1))) {
            i--;
        }

        if (i == 0) {
            throw new IndexOutOfBoundsException();
        }

        i--;

        return i;
    }

    public static int getPrevNonWhitespace(String s) {
        return getPrevNonWhitespace(s, s.length());
    }

    public static boolean hasPrevNonWhitespace(String s, int start) {
        int i = start;

        while (i > 0 && isWhitespace(s.charAt(i - 1))) {
            i--;
        }

        if (i == 0) {
            return false;
        }

        return true;
    }

    public static boolean hasPrevNonWhitespace(String s) {
        return hasPrevNonWhitespace(s, s.length());
    }
}
