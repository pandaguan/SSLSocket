package Sense4;

/**
 * Created by guan on 2017/7/5.
 */
public class JavaSense4 {

    /** S4 API库中使用的各种参数	*/

    /** file types defined
     */
    public static int S4_RSA_PUBLIC_FILE		=0x00000006;
    public static int S4_RSA_PRIVATE_FILE		=0x00000007;
    public static int S4_EXE_FILE				=0x00000008;
    public static int S4_DATA_FILE				=0x00000009;
    public static int S4_XA_EXE_FILE			=0x0000000b;		/** XA User Mode方式编译的Bin文件 */

    /**NetLock license mode*/
    public static int S4_MODULE_MODE        =0x00000000;         /** Module mode*/
    public static int S4_IP_MODE            =0x00000001;         /** IP mode*/

    /** PIN defined */
    public static int S4_USER_PIN				=0x000000a1;
    public static int S4_DEV_PIN				=0x000000a2;
    public static int S4_AUTHEN_PIN				=0x000000a3;

    /** flag defined */
    public static int S4_CREATE_NEW				=0x000000a5;
    public static int S4_UPDATE_FILE			=0x000000a6;
    public static int S4_KEY_GEN_RSA_FILE		=0x000000a7;
    public static int S4_SET_LICENCES			=0x000000a8;
    public static int S4_CREATE_ROOT_DIR		=0x000000ab;
    public static int S4_CREATE_SUB_DIR			=0x000000ac;
    public static int S4_CREATE_MODULE			=0x000000ad;
    public static int S4_FILE_READ_WRITE        =0x00000000;       /** 仅对EXE文件有效：VM可读写，默认值。*/
    public static int S4_FILE_EXECUTE_ONLY      =0x00000100;       /** 仅对EXE文件有效：VM不可读写 */
    public static int S4_CREATE_PEDDING_FILE	=0x00002000;		/** 仅对EXE文件有效：创建填充文件 */

    /**  S4OpenEx参数 */
    public static int S4_EXCLUSIZE_MODE			=0;
    public static int S4_SHARE_MODE				=1;

    /**  S4Control参数ctlCode定义 */
    public static int S4_GET_DEVICE_TYPE		=0x00000025;	/** 取设备类型 */
    public static int S4_GET_VM_TYPE			=0x00000027;	/** 取VM类型 */
    public static int S4_RESET_DEVICE           =0x00000002;	/**重置设备 */
    public static int S4_DF_AVAILABLE_SPACE     =0x00000031;	/** 获得当前目录的剩余空间大小 */
    public static int S4_EF_INFO                =0x00000032;	/** 获得当前目录下某个文件的大小 */
    public static int S4_LED_UP					=0x00000004;	/** LED亮 */
    public static int S4_LED_DOWN				=0x00000008;	/** LED灭 */
    public static int S4_LED_WINK				=0x00000028;	/** LED闪烁 */
    public static int S4_GET_SERIAL_NUMBER		=0x00000026;	/** 取卡片序列号 */
    public static int S4_GET_DEVICE_USABLE_SPACE=0x00000029;	/** 取卡片可用空间 */
    public static int S4_SET_DEVICE_ID			=0x0000002a;	/**  设置用户的ID到设备中 */


    /**  S4Control当参数ctlCode为S4_GET_DEVICE_TYPE时取回的设备类型定义 */
    public static int S4_LOCAL_DEVICE			=0x00;		/** 单机锁 */
    public static int S4_MASTER_DEVICE			=0x80;		/** 网络主锁 */
    public static int S4_SLAVE_DEVICE			=0xc0;		/** 网络从锁 */

    /** S4Control当参数ctlCode为S4_GET_VM_TYPE时取回的VM类型定义 */
    public static int S4_VM_51					=0x00;		/** VM51 */
    public static int S4_VM_251_BINARY			=0x01;		/** VM251 binary */
    public static int S4_VM_251_SOURCE	    	=0X02;		/** VM251 source */


    /** 以下两个参数是给S4ExecuteEx函数使用的 */
    public static int S4_VM_EXE					=0x00000000;		/** 按照调用VM可执行文件方式执行，默认状态 */
    public static int S4_XA_EXE					=0x00000001;		/** 按照调用XA可执行文件方式执行 */


    /** Return Code */
    public static int S4_SUCCESS				=0x00000000;		/** success */
    public static int S4_UNPOWERED				=0x00000001;
    public static int S4_INVALID_PARAMETER		=0x00000002;
    public static int S4_COMM_ERROR				=0x00000003;
    public static int S4_PROTOCOL_ERROR			=0x00000004;
    public static int S4_DEVICE_BUSY			=0x00000005;
    public static int S4_KEY_REMOVED			=0x00000006;
    public static int S4_INSUFFICIENT_BUFFER	=0x00000011;
    public static int S4_NO_LIST				=0x00000012;
    public static int S4_GENERAL_ERROR			=0x00000013;
    public static int S4_UNSUPPORTED			=0x00000014;
    public static int OPEN_LOG_FILE_ERROR		=0x00000015;
    public static int S4_DEVICE_TYPE_MISMATCH	=0x00000020;
    public static int S4_FILE_SIZE_CROSS_7FFF	=0x00000021;
    public static int S4_DEVICE_UNSUPPORTED		=0x00006a81;
    public static int S4_FILE_NOT_FOUND			=0x00006a82;
    public static int S4_INSUFFICIENT_SECU_STATE=0x00006982;
    public static int S4_DIRECTORY_EXIST		=0x00006901;
    public static int S4_FILE_EXIST				=0x00006a80;
    public static int S4_INSUFFICIENT_SPACE		=0x00006a84;
    public static int S4_OFFSET_BEYOND			=0x00006B00;
    public static int S4_PIN_BLOCK				=0x00006983;
    public static int S4_FILE_TYPE_MISMATCH		=0x00006981;
    public static int S4_CRYPTO_KEY_NOT_FOUND	=0x00009403;
    public static int S4_APPLICATION_TEMP_BLOCK	=0x00006985;
    public static int S4_APPLICATION_PERM_BLOCK	=0x00009303;
    public static int S4_DATA_BUFFER_LENGTH_ERROR=0x00006700;
    public static int S4_CODE_RANGE				=0x00010000;
    public static int S4_CODE_RESERVED_INST		=0x00020000;
    public static int S4_CODE_RAM_RANGE			=0x00040000;
    public static int S4_CODE_BIT_RANGE			=0x00080000;
    public static int S4_CODE_SFR_RANGE			=0x00100000;
    public static int S4_CODE_XRAM_RANGE		=0x00200000;

    public static int S4_MODULE_NOT_FOUND		=0x00000301;
    public static int S4_LICENSE_EXIST			=0x00000302;
    public static int S4_USER_NOT_FOUND			=0x00000303;
    public static int S4_LICENSE_INVALID		=0x00000304;
    public static int S4_TIMEOUT				=0x00000305;
    public static int S4_NETWORK_ERROR			=0x00000306;
    public static int S4_LICENSE_NOT_FOUND		=0x00000307;
    public static int S4_EXECUTE_ERROR			=0x00000308;
    public static int S4_TOTALLICENSE_BEYOND	=0x00000309;
    public static int S4_MODULELICENSE_BEYOND	=0x00000310;
    public static int S4_DEVICE_INVALID			=0x00000311;
    public static int S4_USERPIN_ERROR			=0x00000312;
    public static int S4_MODULE_ZERO			=0x00000313;
    public static int S4_DEVICETYPE_ERROR		=0x00000314;
    public static int S4_DEVICE_START_FAILED	=0x00000315;
    public static int S4_DEVICE_STOP_FAILED		=0x00000316;

    public static int S4_ERROR_UNKNOWN			=0xffffffff;

    /** functions */
    //public native int S4Startup();

    //public native int S4Cleanup();

    public native int S4Enum(SENSE4_CONTEXT[] s4_context,
                             int[] size);

    public native int S4Open(SENSE4_CONTEXT s4_context);

    public native int S4OpenEx(SENSE4_CONTEXT s4_context,
                               S4OPENINFO S4OpenInfo);

    public native int S4Close(SENSE4_CONTEXT s4_context);

    public native int S4Control(SENSE4_CONTEXT s4_context,
                                int ctlCode,
                                byte[] inBuff,
                                int inBuffLen,
                                byte[] outBuff,
                                int outBuffLen,
                                int[] bytesReturned);

    public native int S4CreateDir(SENSE4_CONTEXT s4_context,
                                  byte[] lpszDirID,
                                  int dwDirSize,
                                  int dwFlags);

    public native int S4CreateDirEx(  SENSE4_CONTEXT s4_context,
                                      byte[] lpszDirID,
                                      int dwDirSize,
                                      int dwFlags,
                                      S4CREATEDIRINFO CreateDirInfo);

    public native int S4ChangeDir(SENSE4_CONTEXT s4_context,
                                  byte[] lpszPath);

    public native int S4EraseDir(SENSE4_CONTEXT s4_context,
                                 byte[] lpszDirID);

    public native int S4VerifyPin(SENSE4_CONTEXT s4_context,
                                  byte[] lpPin,
                                  int dwPinLen,
                                  int dwPinType);

    public native int S4ChangePin(SENSE4_CONTEXT s4_context,
                                  byte[] lpOldPin,
                                  int dwOldPinLen,
                                  byte[] lpNewPin,
                                  int dwNewPinLen,
                                  int dwPinType);

    public native int S4WriteFile(SENSE4_CONTEXT s4_context,
                                  byte[] lpszFileID,
                                  int dwOffset,
                                  byte[] byteBuffer,
                                  int dwBufferSize,
                                  int dwFileSize,
                                  int[] lpBytesWritten,
                                  int dwFlags,
                                  byte bFileType);

    public native int S4Execute(	SENSE4_CONTEXT s4_context,
                                    byte[] lpszFileID,
                                    byte[] lpInbuffer,
                                    int dwInbufferSize,
                                    byte[] lpOutBuffer,
                                    int dwOutBufferSize,
                                    int[] lpBytesReturned);

    public native int S4ExecuteEx(	SENSE4_CONTEXT s4_context,
                                      byte[] lpszFileID,
                                      int    dwFlag,
                                      byte[] lpInbuffer,
                                      int dwInbufferSize,
                                      byte[] lpOutBuffer,
                                      int dwOutBufferSize,
                                      int[] lpBytesReturned);

    static {
//        System.loadLibrary("sense4");
        System.loadLibrary("JavaSense4Pack");
        //System.load("E:\\eclipseWorkSpaces\\TCPSocket\\src\\JavaSense4Pack.dll");
        /*System.load("E:\\TCPSocket\\sense4.dll");*/
    }
}

/*
将以下的c 的SENSE4_CONTEXT结构体变成java的等价结构体
typedef struct {
        DWORD 			dwIndex;//device index
        DWORD			dwVersion;//version
        HANDLE			hLock;//device handle
        BYTE			reserve[12];
        BYTE			bAtr[MAX_ATR_LEN];
        BYTE			bID[MAX_ID_LEN];
        DWORD			dwAtrLen;
}Sense4.SENSE4_CONTEXT,*PSENSE4_CONTEXT;
*/
class SENSE4_CONTEXT
{
    public int dwIndex;
    public int dwVersion;
    //public byte[] hLock;
    public int hLock;
    public byte[] reserve;
    public byte[] bAtr;
    public byte[] bID;
    public int dwAtrLen;
    SENSE4_CONTEXT()
    {
        reserve = new byte[12];
        bAtr = new byte[56];
        bID = new byte[8];
    }
}
/*
typedef struct{
        WORD EfID;
        BYTE EfType;
        WORD EfSize;
}Sense4.EFINFO,*PEFINFO;
*/
class EFINFO
{
    public short EfID;
    public byte EfType;
    public short EfSize;
}
/*
typedef struct _S4OPENINFO {
        DWORD dwS4OpenInfoSize;
        DWORD dwShareMode;
} Sense4.S4OPENINFO;
*/
class S4OPENINFO
{
    public int dwS4OpenInfoSize;
    public int dwShareMode;
}
/*
typedef struct _S4CREATEDIRINFO {
        DWORD dwS4CreateDirInfoSize;
        BYTE  szAtr[8];
        Sense4.S4NETCONFIG   NetConfig;
} Sense4.S4CREATEDIRINFO;
*/
class S4CREATEDIRINFO
{
    public int dwS4CreateDirInfoSize;
    public byte [] szAtr;
    S4NETCONFIG NetConfig;
    S4CREATEDIRINFO()
    {
        szAtr = new byte[8];
        NetConfig = new S4NETCONFIG();
    }
}
/*
typedef struct _S4NETCONFIG {
  DWORD         dwLicenseMode;
  DWORD         dwModuleCount;
  Sense4.S4MODULEINFO  ModuleInfo[16];
} Sense4.S4NETCONFIG;
*/
class S4NETCONFIG
{
    public int dwLicenseMode;
    public int dwModuleCount;
    public S4MODULEINFO[] ModuleInfo;
    S4NETCONFIG()
    {
        ModuleInfo = new S4MODULEINFO[64];
    }

}

/*
typedef struct _S4MODULEINFO {
  WORD  wModuleID;
  WORD  wLicenseCount;
} Sense4.S4MODULEINFO;
*/
class S4MODULEINFO
{
    public short wModuleID;
    public short wLicenseCount;
}
