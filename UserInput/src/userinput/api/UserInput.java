package userinput.api;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.epsilon.eol.tools.AbstractTool;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class UserInput extends AbstractTool {

	/**
	 * WorkbenchWindow object is used as reference playground to display Dialogs
	 */
	public static IWorkbenchWindow window;
	/**
	 * String name_feature is used to specify the eCore name attribute of a
	 * NamedElement
	 */
	public static String name_feature = null;

	private static class MyLabelProvider extends LabelProvider {

		private String NAME_FEATURE = "name";

		/**
		 * Use the default "name" field for an eCore NamedElement
		 * 
		 */
		@SuppressWarnings("unused")
		public MyLabelProvider() {
			// NOP
		}

		/**
		 * Declare user-defined name field of the eCore object
		 * 
		 * @param nameFeature
		 */
		public MyLabelProvider(String nameFeature) {
			if (nameFeature != null) {
				this.NAME_FEATURE = nameFeature;
			}
		}

		@Override
		public String getText(Object element) {
			// TODO Auto-generated method stub
			try {
				DynamicEObjectImpl obj = (DynamicEObjectImpl) element;
				EClass objClass = ((EObject) element).eClass();
				return (String) obj.dynamicGet(objClass.getEStructuralFeature(this.NAME_FEATURE).getFeatureID());
			} catch (Exception e) {
				e.printStackTrace();
				return super.getText(element);
			}
		}

	}

	private static class VoidValidator implements IInputValidator {
		/**
		 * Validates the String. Returns null for no error, or an error message
		 * 
		 * @param newText the String to validate
		 * @return String
		 */
		@Override
		public String isValid(String newText) {
			return null;
		}
	};

	/**
	 * Display information dialog
	 * 
	 * @param title
	 */
	public void inform(String title) {
		MessageDialog.openInformation(window.getShell(), title, title);
	}

	/**
	 * Display confirmation dialog
	 * 
	 * @param title
	 * @return
	 */
	public boolean confirm(String title) {
		return MessageDialog.openConfirm(window.getShell(), title, title);
	}

	/**
	 * Display String prompt dialog
	 * 
	 * @param title
	 * @param base
	 * @return
	 */
	public String prompt(String title, String base) {
		InputDialog dialog = new InputDialog(window.getShell(), title, title, base, new VoidValidator());
		if (dialog.open() != Window.OK) {
			// User clicked OK; update the label with the input
			return "";
		}
		String result = dialog.getValue();
		return result;
	}

	/**
	 * Display Integer prompt dialog
	 * 
	 * @param title
	 * @param base
	 * @return
	 */
	public int promptInteger(String title, int base) {
		InputDialog dialog = new InputDialog(window.getShell(), title, title, String.valueOf(base),
				new VoidValidator());
		if (dialog.open() != Window.OK) {
			// User clicked OK; update the label with the input
			return base;
		}
		int result;
		try {
			result = Integer.parseInt(dialog.getValue());
		} catch (NumberFormatException e) {
			result = 0;
		}
		return result;
	}

	/**
	 * Display Real prompt dialog
	 * 
	 * @param title
	 * @param base
	 * @return
	 */
	public float promptReal(String title, float base) {
		InputDialog dialog = new InputDialog(window.getShell(), title, title, String.valueOf(base),
				new VoidValidator());
		if (dialog.open() != Window.OK) {
			// User clicked OK; update the label with the input
			return base;
		}
		float result;
		try {
			result = Float.parseFloat(dialog.getValue());
		} catch (NumberFormatException e) {
			result = 0;
		}
		return result;
	}

	/**
	 * Display Single Choice dialog
	 * 
	 * @param title
	 * @param list
	 * @param base
	 * @return
	 */
	public Object choose(String title, List<Object> list, Object base) {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(),
				new MyLabelProvider(name_feature));
		dialog.setElements(list.toArray());
		dialog.setTitle(title);
		dialog.setMultipleSelection(false);
		dialog.setInitialElementSelections(Arrays.asList(base));
		// user pressed cancel
		if (dialog.open() != Window.OK) {
			return base;
		}
		Object result = dialog.getResult()[0];
		return result;
	}

	/**
	 * Display Multiple Choice dialog
	 * 
	 * @param title
	 * @param list
	 * @param base
	 * @return
	 */
	public Object chooseMany(String title, List<Object> list, List<Object> base) {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(),
				new MyLabelProvider(name_feature));
		dialog.setElements(list.toArray());
		dialog.setTitle(title);
		dialog.setMultipleSelection(true);
		dialog.setInitialElementSelections(base);
		// user pressed cancel
		if (dialog.open() != Window.OK) {
			return base;
		}
		Object[] result = dialog.getResult();
		return result;
	}
}
