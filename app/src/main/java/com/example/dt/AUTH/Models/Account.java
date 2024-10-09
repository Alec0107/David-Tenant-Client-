package com.example.dt.AUTH.Models;

public class Account {

        private String username;
        private String email;
        private String password;

        public Account(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public Account(String email, String password){
            this.email    = email;
            this.password = password;
        }


        @Override
        public String toString() {
            return
                    "username='" + username + '\'' +
                            ", email='" + email + '\'' +
                            ", password='" + password + '\'' +
                            '}';
        }

}
