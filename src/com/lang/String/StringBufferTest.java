package com.lang.String;

/**
 * @author liqiao
 * @date 2020/5/23

 */

public class StringBufferTest {
    public static void main(String[] args) {
        //public class com.lang.String.StringBufferTest {
        //  public com.lang.String.StringBufferTest();
        //    Code:
        //       0: aload_0
        //       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
        //       4: return
        //
        //  public static void main(java.lang.String[]);
        //    Code:
        //       0: new           #2                  // class java/lang/StringBuffer
        //       3: dup
        //       4: invokespecial #3                  // Method java/lang/StringBuffer."<init>":()V
        //       7: astore_1
        //       8: aload_1
        //       9: ldc           #4                  // String a
        //      11: invokevirtual #5                  // Method java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //      14: ldc           #6                  // String b
        //      16: invokevirtual #5                  // Method java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //      19: ldc           #7                  // String c
        //      21: invokevirtual #5                  // Method java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //      24: pop
        //      25: return
        //}
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("a").append("b").append("c");


        int i = 0;
        StringBuffer sb = new StringBuffer();
        while (i < 100) {
            sb.append("str");
            i++;
        }
        System.out.println(sb.toString());
    }


}
