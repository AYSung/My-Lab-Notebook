package labnotebook.GUI;

import javax.swing.*;

public abstract class InputDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	protected Main main;

	protected InputDialog(Main main) {
		this.main = main;
	}
	
	protected void initialize(){
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(main);
	}
	
	protected InputDialog getInputDialog(){
		return this;
	}
}
