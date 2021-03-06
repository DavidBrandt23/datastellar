import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.lang3.text.WordUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;


public class UpdateSelectGalaxyPage {
	public static Composite createSelectUpdatePage(Composite parent, Connection conn) {

		// This page is just for selecting what planet we want to update
		// All it has is a drop-down filled with planet names and the composite to swap
		// in for the selected planet's data
		// For things like moons or stars you'll want to copy this and the Update[TYPE]Page.java
		Composite updatePage = new Composite(parent, SWT.NONE);
		GridLayout updateGL = new GridLayout(4, true);

		GridData updateGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
		Label galaxyUpdateName = new Label(updatePage, SWT.NONE);
		updateGD.horizontalSpan = 4;
		galaxyUpdateName.setText("Select a Galaxy to update:");
		galaxyUpdateName.setLayoutData(updateGD);

		updateGD = new GridData(SWT.FILL, SWT.CENTER, false, false);
		Combo updateGalaxySelect = new Combo(updatePage, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		updateGD.horizontalSpan = 2;
		updateGalaxySelect.setLayoutData(updateGD);
		
		Composite sharedComposite = new Composite(updatePage, SWT.BORDER);
		updateGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		updateGD.horizontalSpan = 4;
		sharedComposite.setLayoutData(updateGD);
		StackLayout sl = new StackLayout();
		sharedComposite.setLayout(sl);

		//Just get all the names of the planets
		try {
			PreparedStatement getTableNames = conn
					.prepareStatement("SELECT name FROM galaxy;");
			getTableNames.execute();
			ResultSet rs = getTableNames.getResultSet();
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String columnValue = rs.getString(i);
					updateGalaxySelect.add(WordUtils.capitalize(columnValue));
				}
			}

		} catch (SQLException e) {
			System.out.println("SQL Error");
			e.printStackTrace();
		}
		
		updateGalaxySelect.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Composite galaxyPage = UpdateGalaxyPage.updateGalaxyPage(sharedComposite, conn, updateGalaxySelect.getText(), updateGalaxySelect);
					sl.topControl = galaxyPage;
					sharedComposite.layout();
			}});

		updatePage.setLayout(updateGL);
		return updatePage;
	}
}
