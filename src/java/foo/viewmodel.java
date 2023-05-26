//package data;
package foo;
import data.Mahasiswa;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Textbox;
import static sun.jvm.hotspot.HelloWorld.e;

public class viewmodel extends SelectorComposer<Window> {
    
    private List<Mahasiswa> mahasiswaList;
   
    @Wire
    private Textbox nametxt;
    @Wire
    private Textbox emailtxt;
    @Wire
    private Textbox addresstxt;
    @Wire
    private Textbox phonetxt;
    @Wire
    private Listbox lb;
    @Wire
    private Popup popup;
    @Wire("#nameLabel")
    private Label nameLabel;
    @Wire("#emailLabel")
    private Label emailLabel;
    
    @Listen("onSelect = #lb")
    public void onSelect(Event event) {
        Listitem selectedItem = lb.getSelectedItem();
        if (selectedItem != null) {
            String name = (String) selectedItem.getAttribute("name");
            String email = (String) selectedItem.getAttribute("email");
            
            nameLabel.setValue(name);
            emailLabel.setValue(email);
            
            popup.open(lb, "after_start");
        }
    }
    
    @Listen("onClick = #add")
    public void Submit() throws SQLException {
        
        String name = nametxt.getValue();
        String email = emailtxt.getValue();
        String address = addresstxt.getValue();
        String phone = phonetxt.getValue();
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/app", "root", "123");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO mahasiswa (nama, email, address, phone) VALUES (?, ?, ?, ?)");
            
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, address);
            statement.setString(4, phone);
                        
            statement.executeUpdate();
            
            statement.close();
            connection.close();                
            
            Messagebox.show("Data inserted successfully!", "Information", Messagebox.OK, Messagebox.INFORMATION, new ClickListener() {
            public void onClick(ClickEvent event) {

                Executions.sendRedirect(null);

            }
        });           
           
        }
        catch (SQLException e) {
            e.printStackTrace();
            Messagebox.show("Error occurred while inserting data.");
        }
    }

    
    void loadData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/app", "root", "123");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mahasiswa");

            mahasiswaList = new ArrayList<>();
            while (resultSet.next()) {
                Mahasiswa data = new Mahasiswa();
                data.setNama(resultSet.getString("nama"));
                data.setEmail(resultSet.getString("email"));
                data.setPhone(resultSet.getString("phone"));
                data.setAddress(resultSet.getString("address"));
                mahasiswaList.add(data);
            }
            
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException e) {
            // Handle error
            e.printStackTrace();
            Messagebox.show("Error occurred while inserting data.");
        }
    }

  @Listen("onClick = #deleteButton")  
  public void deleteData() throws SQLException {
    
    try {
      // Hapus data mahasiswa dari database
      Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/app", "root", "123");
      PreparedStatement statement = connection.prepareStatement("DELETE FROM mahasiswa WHERE nama = ?");
      
      statement.setString(1, "Ibnu Wildan");
 
      int rowsDeleted = statement.executeUpdate();
      
      if (rowsDeleted > 0) {
         Messagebox.show("Data deleted successfully!");
         Executions.sendRedirect(null);
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
      Messagebox.show("Error occurred while deleting data.");
    }
  }
  
    
    
}

