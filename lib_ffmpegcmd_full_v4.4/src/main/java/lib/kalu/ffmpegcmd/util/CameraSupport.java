package lib.kalu.ffmpegcmd.util;

import static android.content.Context.CAMERA_SERVICE;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.ffmpegcmd.cmd.Cmd;

public class CameraSupport {

    /**
     * <p>Compatibility method for extracting supported camera ids.
     *
     * @param context application context
     * @return returns the list of supported camera ids
     */
    public static List<String> extractSupportedCameraIds(final Context context) {
        final List<String> detectedCameraIdList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                final CameraManager manager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
                if (manager != null) {
                    final String[] cameraIdList = manager.getCameraIdList();

                    for (String cameraId : cameraIdList) {
                        final CameraCharacteristics chars = manager.getCameraCharacteristics(cameraId);
                        final Integer cameraSupport = chars.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);

                        if (cameraSupport != null && cameraSupport == CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                            Log.d(Cmd.TAG, "Detected camera with id " + cameraId + " has LEGACY hardware level which is not supported by Android Camera2 NDK API.");
                        } else if (cameraSupport != null) {
                            detectedCameraIdList.add(cameraId);
                        }
                    }
                }
            } catch (final CameraAccessException e) {
                Log.w(Cmd.TAG, "Detecting camera ids failed.", e);
            }
        }

        return detectedCameraIdList;
    }

}
