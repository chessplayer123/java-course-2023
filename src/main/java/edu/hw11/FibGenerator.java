package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Ownership;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public final class FibGenerator {
    private static final String CLASS_NAME = "Fib";
    private static final String METHOD_NAME = "fib";
    private static final String METHOD_SIGNATURE = "(I)J";
    private static final int OPERAND_STACK_SIZE = 5; // long + this + int (1 + 2 + 2)

    private FibGenerator() {
    }

    public static Object generate()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return new ByteBuddy()
            .subclass(Object.class)
            .name(CLASS_NAME)
            .defineMethod(METHOD_NAME, long.class, Ownership.MEMBER, Visibility.PUBLIC)
            .withParameters(int.class)
            .intercept(new Implementation.Simple(new FibGeneratorAppender()))
            .make()
            .load(FibGenerator.class.getClassLoader())
            .getLoaded()
            .getConstructor()
            .newInstance();
    }

    private static class FibGeneratorAppender implements ByteCodeAppender {
        @Override
        @NotNull
        public Size apply(
            @NotNull MethodVisitor mv,
            @NotNull Implementation.Context context,
            @NotNull MethodDescription methodDescription
        ) {
            // long fib(int n) {
            //     if (n < 2) {
            //         return 1L;
            //     }
            //     return fib(n-1) + fib(n-2);
            // }

            Label greaterOrEqualTwoBranch = new Label();

            mv.visitVarInsn(Opcodes.ILOAD, 1); // n
            mv.visitInsn(Opcodes.ICONST_2);    // 2
            mv.visitJumpInsn(Opcodes.IF_ICMPGT, greaterOrEqualTwoBranch);
            // n < 2
            mv.visitInsn(Opcodes.LCONST_1); // 1L
            mv.visitInsn(Opcodes.LRETURN);  // return 1L;
            // n >= 2
            mv.visitLabel(greaterOrEqualTwoBranch);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            mv.visitVarInsn(Opcodes.ALOAD, 0); // this
            mv.visitVarInsn(Opcodes.ILOAD, 1); // n
            mv.visitInsn(Opcodes.ICONST_1);    // 1
            mv.visitInsn(Opcodes.ISUB);        // n - 1
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, CLASS_NAME, METHOD_NAME, METHOD_SIGNATURE, false); // fib(n-1)

            mv.visitVarInsn(Opcodes.ALOAD, 0); // this
            mv.visitVarInsn(Opcodes.ILOAD, 1); // n
            mv.visitInsn(Opcodes.ICONST_2);    // 2
            mv.visitInsn(Opcodes.ISUB);        // n - 2
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, CLASS_NAME, METHOD_NAME, METHOD_SIGNATURE, false); // fib(n-2)

            mv.visitInsn(Opcodes.LADD); // fib(n-1) + fib(n-2)
            mv.visitInsn(Opcodes.LRETURN);

            return new Size(OPERAND_STACK_SIZE, 0);
        }
    }
}
