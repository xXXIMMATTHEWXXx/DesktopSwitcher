package codes.matthewp.desktopchanger.core;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

import java.util.HashMap;


public interface SPI extends StdCallLibrary {

    long SPI_SETDESKWALLPAPER = 20;
    long SPIF_UPDATEINIFILE = 0x01;
    long SPIF_SENDWININICHANGE = 0x02;

    /**
     * Loads the Windows User32 Library, maps our call to the windows api then calls it
     */
    SPI INSTANCE = Native.loadLibrary("user32", SPI.class, new HashMap<String, Object>() {
        {
            put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
            put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
        }
    });

    boolean SystemParametersInfo(
            WinDef.UINT_PTR uiAction,
            WinDef.UINT_PTR uiParam,
            String pvParam,
            WinDef.UINT_PTR fWinIni
    );
}