package com.feicui.edu.highpart.common;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class GetPictureUtil {
    public static final int REQUEST_CAMERA = 1;// 拍照
    public static final int REQUEST_GALLERY = 2;// 相册
    public static final int REQUEST_CROP = 3;// 裁剪

    public static final int AVATAR_WIDTH = 300;
    public static final int AVATAR_HEIGHT = 300;

    public static void openCamera(Activity activity) {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File path = getFilePath(activity);
        if (null != path) {
            Uri uri = Uri.fromFile(path);
            imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            imageCaptureIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivityForResult(imageCaptureIntent, REQUEST_CAMERA);
        }
    }

    public static void openCamera(Fragment activity) {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File path = getFilePath(activity.getActivity());
        if (null != path) {
            Uri uri = Uri.fromFile(path);
            imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            imageCaptureIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivityForResult(imageCaptureIntent, REQUEST_CAMERA);
        }
    }

    public static void openGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        final Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }
    public static void openGallery(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        final Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        fragment.startActivityForResult(intent, REQUEST_GALLERY);
    }

    public static File getFilePath(Activity activity) {
        File folder = new File(activity.getExternalFilesDir(null), "temp");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!folder.canWrite()) {
            Toast.makeText(activity, "未检测到外部存储，图片功能不可用。", Toast.LENGTH_SHORT).show();
            return null;
        }
        return new File(folder, activity.getClass().getSimpleName()+ ".jpg");
    }

    public static String getFilePathString(Activity activity) {
        File file = getFilePath(activity);
        if (file != null) {
            return file.getPath();
        }
        return null;
    }

    public static void clearTemp(Activity activity) {
        File folder = new File(activity.getExternalFilesDir(null), "temp");
        if (null != folder.listFiles()) {
            for (File file : folder.listFiles()) {
                file.delete();
            }
        }
    }

    /**
     * @param requestCode
     * @param data
     * @param activity
     * @param isCrop      是否需要裁剪图片
     * @param width       裁剪宽度
     * @param height      裁剪高度
     * @return
     */
    public static String onActivityResult(int requestCode, Intent data, Activity activity, boolean isCrop, int width, int height) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                String savePath = getFilePathString(activity);
                if (savePath != null) {
                    processPicture(savePath, savePath);
                    if (isCrop) {
                        cropPicture(activity, savePath, width, height);
                        return null;
                    }
                }
                return savePath;
            }
            case REQUEST_GALLERY: {
                Uri selectedImage = data.getData();
                String readPicturePath = null;
                String filemanagerstring = selectedImage.getPath();

                String selectedImagePath = null;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query
                        (selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    selectedImagePath = cursor.getString(columnIndex);
                    cursor.close();
                }

                if (selectedImagePath != null) {
                    readPicturePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    readPicturePath = filemanagerstring;
                }
                if (readPicturePath == null) {
                    return null;
                }

                String savePath = getFilePathString(activity);
                if (savePath != null) {
                    processPicture(readPicturePath, savePath);
                    if (isCrop) {
                        cropPicture(activity, savePath, width, height);
                        return null;
                    }
                }
                return savePath;
            }
            case REQUEST_CROP:
                return getFilePathString(activity);
        }
        return null;
    }

    /**
     * @param requestCode
     * @param data
     * @param fragment
     * @param isCrop      是否需要裁剪图片
     * @param width       裁剪宽度
     * @param height      裁剪高度
     * @return
     */
    public static String onActivityResult(int requestCode, Intent data, Fragment fragment, boolean isCrop, int width, int height) {
        Activity activity = fragment.getActivity();
        switch (requestCode) {
            case REQUEST_CAMERA: {
                String savePath = getFilePathString(activity);
                if (savePath != null) {
                    processPicture(savePath, savePath);
                    if (isCrop) {
                        cropPicture(activity, savePath, width, height);
                        return null;
                    }
                }
                return savePath;
            }
            case REQUEST_GALLERY: {
                Uri selectedImage = data.getData();
                String readPicturePath = null;
                String filemanagerstring = selectedImage.getPath();

                String selectedImagePath = null;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query
                        (selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    selectedImagePath = cursor.getString(columnIndex);
                    cursor.close();
                }

                if (selectedImagePath != null) {
                    readPicturePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    readPicturePath = filemanagerstring;
                }
                if (readPicturePath == null) {
                    return null;
                }

                String savePath = getFilePathString(activity);
                if (savePath != null) {
                    processPicture(readPicturePath, savePath);
                    if (isCrop) {
                        cropPicture(activity, savePath, width, height);
                        return null;
                    }
                }
                return savePath;
            }
            case REQUEST_CROP:
                return getFilePathString(activity);
        }
        return null;
    }

    /**
     * 处理图片
     *
     * @param srcPath 源图片路径
     * @param dstPath 处理后的图片路径
     */
    private static void processPicture(String srcPath, String dstPath) {
        int degree = ImageDispose.readPictureDegree(srcPath);//读取拍摄角度
        Bitmap bitmap = ImageDispose.decodeSampledBitmapFromFile(srcPath, 1280);
        Bitmap bitmap2 = ImageDispose.scaleRotateBitmap(degree, bitmap, 1280);//压缩到实际要求的分辨率并旋转图片到正确方向
        ImageDispose.saveBitmap(dstPath, bitmap2);//保存到本地
        bitmap.recycle();
        bitmap2.recycle();
        bitmap = null;
        bitmap2 = null;
        System.gc();
    }

    public static String onActivityResult(int requestCode, Intent data, Activity activity) {
        return onActivityResult(requestCode, data, activity, false, 0, 0);
    }

    public static String onActivityResult(int requestCode, Intent data, Fragment fragment) {
        return onActivityResult(requestCode, data, fragment, false, 0, 0);
    }

    private static void cropPicture(Activity activity, String path, int width, int height) {
        Uri uri = Uri.fromFile(new File(path));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", width);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", height);

        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", "JPEG");//返回格式
        activity.startActivityForResult(intent, REQUEST_CROP);
    }
}

