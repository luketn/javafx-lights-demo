open module javafxtemplate {
	uses com.mycodefu.afterburner.injection.PresenterFactory;

	requires java.annotation;
	requires java.desktop;
	requires javafx.controls;
	requires javafx.fxml;
	requires javax.inject;
	requires javafx.base;
	requires javafx.swing;
	requires org.controlsfx.controls;

	exports com.mycodefu.dashboard.tables to org.controlsfx.controls;
}