package crc64f0d106695cb1f69e;


public class TextBoxEx_LayoutListener
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		android.view.ViewTreeObserver.OnGlobalLayoutListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onGlobalLayout:()V:GetOnGlobalLayoutHandler:Android.Views.ViewTreeObserver/IOnGlobalLayoutListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n" +
			"";
		mono.android.Runtime.register ("Syncfusion.SfPdfViewer.XForms.Droid.TextBoxEx+LayoutListener, Syncfusion.SfPdfViewer.XForms.Android", TextBoxEx_LayoutListener.class, __md_methods);
	}


	public TextBoxEx_LayoutListener ()
	{
		super ();
		if (getClass () == TextBoxEx_LayoutListener.class)
			mono.android.TypeManager.Activate ("Syncfusion.SfPdfViewer.XForms.Droid.TextBoxEx+LayoutListener, Syncfusion.SfPdfViewer.XForms.Android", "", this, new java.lang.Object[] {  });
	}

	public TextBoxEx_LayoutListener (crc64f0d106695cb1f69e.TextBoxEx p0)
	{
		super ();
		if (getClass () == TextBoxEx_LayoutListener.class)
			mono.android.TypeManager.Activate ("Syncfusion.SfPdfViewer.XForms.Droid.TextBoxEx+LayoutListener, Syncfusion.SfPdfViewer.XForms.Android", "Syncfusion.SfPdfViewer.XForms.Droid.TextBoxEx, Syncfusion.SfPdfViewer.XForms.Android", this, new java.lang.Object[] { p0 });
	}


	public void onGlobalLayout ()
	{
		n_onGlobalLayout ();
	}

	private native void n_onGlobalLayout ();

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}
