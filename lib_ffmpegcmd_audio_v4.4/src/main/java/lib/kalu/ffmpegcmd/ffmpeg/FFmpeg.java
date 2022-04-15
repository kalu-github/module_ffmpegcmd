package lib.kalu.ffmpegcmd.ffmpeg;

import android.os.AsyncTask;

import lib.kalu.ffmpegcmd.async.AsyncFFmpegExecuteTask;
import lib.kalu.ffmpegcmd.callback.ExecuteCallback;
import lib.kalu.ffmpegcmd.cmd.Cmd;
import lib.kalu.ffmpegcmd.entity.FFmpegExecution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

public class FFmpeg {

    private static final String FFMPEG = "ffmpeg";
    public static final long DEFAULT_EXECUTION_ID = 0;

    private static final AtomicLong executionIdCounter = new AtomicLong(3000);

//    static {
//        AbiDetect.class.getName();
//        Config.class.getName();
//    }

    /**
     * Default constructor hidden.
     */
    private FFmpeg() {
    }

    /**
     * <p>Synchronously executes FFmpeg with arguments provided.
     *
     * @param arguments FFmpeg command options/arguments as string array
     * @return 0 on successful execution, 255 on user cancel, other non-zero codes on error
     */
    public static int execute(final List<String> arguments) {

        if (null == arguments || arguments.size() == 0)
            return -1;

        if (arguments.size() == 1 && arguments.get(0).equalsIgnoreCase(FFMPEG))
            return -2;

        ArrayList<String> list = new ArrayList<>(arguments);
        if (list.get(0).equalsIgnoreCase(FFMPEG)) {
            list.remove(0);
        }

        return Cmd.ffmpegExecute(DEFAULT_EXECUTION_ID, list.toArray(new String[]{}));
    }


    public static int execute(final String[] arguments) {
        return Cmd.ffmpegExecute(DEFAULT_EXECUTION_ID, arguments);
    }

//    /**
//     * <p>Asynchronously executes FFmpeg with arguments provided.
//     *
//     * @param arguments       FFmpeg command options/arguments as string array
//     * @param executeCallback callback that will be notified when execution is completed
//     * @return returns a unique id that represents this execution
//     */
//    public static long executeAsync(final String[] arguments, final ExecuteCallback executeCallback) {
//        final long newExecutionId = executionIdCounter.incrementAndGet();
//        AsyncFFmpegExecuteTask asyncFFmpegExecuteTask = new AsyncFFmpegExecuteTask(newExecutionId, arguments,  executeCallback);
//        asyncFFmpegExecuteTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        return newExecutionId;
//    }

    /**
     * <p>Asynchronously executes FFmpeg with arguments provided.
     *
     * @param arguments       FFmpeg command options/arguments as string array
     * @param executeCallback callback that will be notified when execution is completed
     * @param executor        executor that will be used to run this asynchronous operation
     * @return returns a unique id that represents this execution
     */
    public static long executeAsync(final String[] arguments, final ExecuteCallback executeCallback, final Executor executor) {
        final long newExecutionId = executionIdCounter.incrementAndGet();

        AsyncFFmpegExecuteTask asyncFFmpegExecuteTask = new AsyncFFmpegExecuteTask(newExecutionId, arguments, executeCallback);
        asyncFFmpegExecuteTask.executeOnExecutor(executor);

        return newExecutionId;
    }

    /**
     * <p>Synchronously executes FFmpeg command provided. Command is split into arguments using
     * provided delimiter character.
     *
     * @param command   FFmpeg command
     * @param delimiter delimiter used to split arguments
     * @return 0 on successful execution, 255 on user cancel, other non-zero codes on error
     * @since 3.0
     * @deprecated argument splitting mechanism used in this method is pretty simple and prone to
     * errors. Consider using a more advanced method like {@link #execute(String)} or
     * {@link #execute(String[])}
     */
    public static int execute(final String command, final String delimiter) {
        return execute((command == null) ? new String[]{""} : command.split((delimiter == null) ? " " : delimiter));
    }

    /**
     * <p>Synchronously executes FFmpeg command provided. Space character is used to split command
     * into arguments. You can use single and double quote characters to specify arguments inside
     * your command.
     *
     * @param command FFmpeg command
     * @return 0 on successful execution, 255 on user cancel, other non-zero codes on error
     */
    public static int execute(final String command) {
        String[] arguments = format(command);
        return execute(arguments);
    }

//    /**
//     * <p>Asynchronously executes FFmpeg command provided. Space character is used to split command
//     * into arguments. You can use single and double quote characters to specify arguments inside
//     * your command.
//     *
//     * @param command         FFmpeg command
//     * @param executeCallback callback that will be notified when execution is completed
//     * @return returns a unique id that represents this execution
//     */
//    public static long executeAsync(final String command, final ExecuteCallback executeCallback) {
//        final long newExecutionId = executionIdCounter.incrementAndGet();
//        AsyncFFmpegExecuteTask asyncFFmpegExecuteTask = new AsyncFFmpegExecuteTask(newExecutionId, command,  executeCallback);
//        asyncFFmpegExecuteTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        return newExecutionId;
//    }

    public static long executeAsync(final String command, final ExecuteCallback executeCallback) {
        final long newExecutionId = executionIdCounter.incrementAndGet();
        String[] arguments = format(command);
        AsyncFFmpegExecuteTask asyncFFmpegExecuteTask = new AsyncFFmpegExecuteTask(newExecutionId, arguments, executeCallback);
        asyncFFmpegExecuteTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return newExecutionId;
    }

    public static long executeAsync(String[] cmds, ExecuteCallback executeCallback) {
        final long newExecutionId = executionIdCounter.incrementAndGet();
        AsyncFFmpegExecuteTask asyncFFmpegExecuteTask = new AsyncFFmpegExecuteTask(newExecutionId, cmds, executeCallback);
        asyncFFmpegExecuteTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return newExecutionId;
    }

    public static long executeAsync(List<String> cmds, ExecuteCallback executeCallback) {

        if (null == cmds || cmds.size() == 0)
            return -1;

        if (cmds.size() == 1 && cmds.get(0).equalsIgnoreCase(FFMPEG))
            return -2;

        ArrayList<String> list = new ArrayList<>(cmds);
        if (list.get(0).equalsIgnoreCase(FFMPEG)) {
            list.remove(0);
        }

        final long newExecutionId = executionIdCounter.incrementAndGet();
        AsyncFFmpegExecuteTask asyncFFmpegExecuteTask = new AsyncFFmpegExecuteTask(newExecutionId, list.toArray(new String[]{}), executeCallback);
        asyncFFmpegExecuteTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return newExecutionId;
    }

    /**
     * <p>Asynchronously executes FFmpeg command provided. Space character is used to split command
     * into arguments. You can use single and double quote characters to specify arguments inside
     * your command.
     *
     * @param command         FFmpeg command
     * @param executeCallback callback that will be notified when execution is completed
     * @param executor        executor that will be used to run this asynchronous operation
     * @return returns a unique id that represents this execution
     */
    public static long executeAsync(final String command, final ExecuteCallback executeCallback, final Executor executor) {
        final long newExecutionId = executionIdCounter.incrementAndGet();
        String[] arguments = format(command);
        AsyncFFmpegExecuteTask asyncFFmpegExecuteTask = new AsyncFFmpegExecuteTask(newExecutionId, arguments, executeCallback);
        asyncFFmpegExecuteTask.executeOnExecutor(executor);

        return newExecutionId;
    }

    /**
     * <p>Cancels an ongoing operation.
     *
     * <p>This function does not wait for termination to complete and returns immediately.
     */
    public static void cancel() {
        Cmd.nativeFFmpegCancel(DEFAULT_EXECUTION_ID);
    }

    /**
     * <p>Cancels an ongoing operation.
     *
     * <p>This function does not wait for termination to complete and returns immediately.
     *
     * @param executionId id of the execution
     */
    public static void cancel(final long executionId) {
        Cmd.nativeFFmpegCancel(executionId);
    }

    /**
     * <p>Lists ongoing executions.
     *
     * @return list of ongoing executions
     */
    public static List<FFmpegExecution> listExecutions() {
        return Cmd.listFFmpegExecutions();
    }

    private static String[] format(final String command) {
        final List<String> argumentList = new ArrayList<>();
        StringBuilder currentArgument = new StringBuilder();

        boolean singleQuoteStarted = false;
        boolean doubleQuoteStarted = false;

        for (int i = 0; i < command.length(); i++) {
            final Character previousChar;
            if (i > 0) {
                previousChar = command.charAt(i - 1);
            } else {
                previousChar = null;
            }
            final char currentChar = command.charAt(i);

            if (currentChar == ' ') {
                if (singleQuoteStarted || doubleQuoteStarted) {
                    currentArgument.append(currentChar);
                } else if (currentArgument.length() > 0) {
                    argumentList.add(currentArgument.toString());
                    currentArgument = new StringBuilder();
                }
            } else if (currentChar == '\'' && (previousChar == null || previousChar != '\\')) {
                if (singleQuoteStarted) {
                    singleQuoteStarted = false;
                } else if (doubleQuoteStarted) {
                    currentArgument.append(currentChar);
                } else {
                    singleQuoteStarted = true;
                }
            } else if (currentChar == '\"' && (previousChar == null || previousChar != '\\')) {
                if (doubleQuoteStarted) {
                    doubleQuoteStarted = false;
                } else if (singleQuoteStarted) {
                    currentArgument.append(currentChar);
                } else {
                    doubleQuoteStarted = true;
                }
            } else {
                currentArgument.append(currentChar);
            }
        }

        if (currentArgument.length() > 0) {
            argumentList.add(currentArgument.toString());
        }

        return argumentList.toArray(new String[0]);
    }

    public static String argumentsToString(final String[] arguments) {
        if (arguments == null) {
            return "null";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arguments.length; i++) {
            if (i > 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(arguments[i]);
        }

        return stringBuilder.toString();
    }
}
