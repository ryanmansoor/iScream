//Code to create a simple authorizing function with a hardcoded username and password.
public void checkUserPass(){
					String Username = txtUsername.getText();
					char[] Pass = txtPassword.getPassword();
					String Password = new String(Pass);
					if ((Username.equals("admin"))&& (Password.equals("12345"))){
						dispose();
						MainMenu s = new MainMenu();
						s.setVisible(true);
						txtPassword.setText(null);
						txtUsername.requestFocus(true);
						}else{msgBox.infoBox("Incorrect Username/Password Combination", "Please try again");
						txtPassword.setText(null);
						txtUsername.requestFocus(true);
					}
	   } 
