package com.nabih.wikaya;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String, Void, String> {
    String JSON_STRING;
    Context ctx;
    public AsyncResponse delegate = null;

    BackgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {
        HttpURLConnection httpURLConnection;
        OutputStream OS;
        BufferedWriter bufferedWriter;
        InputStream input_stream;
        BufferedReader buffered_reader;
        String string_response, str, string_first_name, string_last_name, string_email, string_phone_number, string_password,
                string_token, string_user_id, string_place_name, string_address, string_latitude, string_longitude, string_location_type,
                string_residential_type, string_property_number, string_number_ppl_allowed, string_place_id, string_section, string_purpose_visit;




        String method = params[0];


        if (method.equals("method_login")) {
            String url = "http://bespouse.com/wikaya/w_tools/w_login.php";
            string_email = params[1];
            string_password = params[2];



            try {
                httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(string_email, "UTF-8") +
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(string_password, "UTF-8"));
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_register")) {
            String url_register = "http://bespouse.com/wikaya/w_add/w_register.php";
            string_first_name = params[1];
            string_last_name = params[2];
            string_email = params[3];
            string_phone_number = params[4];
            string_password = params[5];



            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(string_first_name, "UTF-8") +
                        "&" + URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(string_last_name, "UTF-8")+
                        "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(string_email, "UTF-8")+
                        "&" + URLEncoder.encode("phone_number", "UTF-8") + "=" + URLEncoder.encode(string_phone_number, "UTF-8")+
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(string_password, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_check_email_exist")) {
            String url_register = "http://bespouse.com/wikaya/w_tools/w_check_email_exist.php";
            string_email = params[1];

            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(string_email, "UTF-8"));
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_send_token")) {
            String url_register = "http://bespouse.com/wikaya/w_tools/w_send_token.php";
            string_email = params[1];
            string_token = params[2];

            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(string_email, "UTF-8") +
                        "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(string_token, "UTF-8")
                );                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }



        }else if (method.equals("method_update_password")) {
            String url_register = "http://bespouse.com/wikaya/w_update/w_update_password.php";
            string_email = params[1];
            string_password = params[2];

            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(string_email, "UTF-8") +
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(string_password, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


             
        }else if (method.equals("method_create_place")) {
            String url_register = "http://bespouse.com/wikaya/w_add/w_add_place.php";
            string_user_id = params[1];
            string_place_name = params[2];
            string_address = params[3];
            string_latitude = params[4];
            string_longitude = params[5];
            string_location_type = params[6];
            string_section = params[7];
            string_residential_type = params[8];
            string_number_ppl_allowed = params[9];
            string_property_number = params[10];


            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8") +
                        "&" + URLEncoder.encode("place_name", "UTF-8") + "=" + URLEncoder.encode(string_place_name, "UTF-8")+
                        "&" + URLEncoder.encode("place_address", "UTF-8") + "=" + URLEncoder.encode(string_address, "UTF-8")+
                        "&" + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(string_latitude, "UTF-8")+
                        "&" + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(string_longitude, "UTF-8")+
                        "&" + URLEncoder.encode("location_type", "UTF-8") + "=" + URLEncoder.encode(string_location_type, "UTF-8")+
                        "&" + URLEncoder.encode("residential_type", "UTF-8") + "=" + URLEncoder.encode(string_residential_type, "UTF-8")+
                        "&" + URLEncoder.encode("section", "UTF-8") + "=" + URLEncoder.encode(string_section, "UTF-8") +
                        "&" + URLEncoder.encode("number_people_allowed", "UTF-8") + "=" + URLEncoder.encode(string_number_ppl_allowed, "UTF-8") +
                        "&" + URLEncoder.encode("property_number", "UTF-8") + "=" + URLEncoder.encode(string_property_number, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_get_health_status_and_place_info")) {
            String url_register = "http://bespouse.com/wikaya/w_get/w_get_health_condition_place_info.php";
            string_user_id = params[1];
            string_place_id = params[2];



            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")+
                        "&" + URLEncoder.encode("place_id", "UTF-8") + "=" + URLEncoder.encode(string_place_id, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_check_in")) {
            String url_register = "http://bespouse.com/wikaya/w_add/w_add_check_in.php";
            string_user_id = params[1];
            string_place_id = params[2];
            string_purpose_visit = params[3];
            string_place_name = params[4];



            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")+
                        "&" + URLEncoder.encode("place_id", "UTF-8") + "=" + URLEncoder.encode(string_place_id, "UTF-8")+
                        "&" + URLEncoder.encode("purpose_visit", "UTF-8") + "=" + URLEncoder.encode(string_purpose_visit, "UTF-8")+
                        "&" + URLEncoder.encode("place_name", "UTF-8") + "=" + URLEncoder.encode(string_place_name, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_get_checked_in_data")) {
            String url_register = "http://bespouse.com/wikaya/w_get/w_get_checked_in_data.php";
            string_user_id = params[1];




            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_check_out")) {
            String url_register = "http://bespouse.com/wikaya/w_update/w_check_out.php";
            string_user_id = params[1];
            string_place_id = params[2];




            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")+
                        "&" + URLEncoder.encode("place_id", "UTF-8") + "=" + URLEncoder.encode(string_place_id, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_cancel_check_in")) {
            String url_register = "http://bespouse.com/wikaya/w_delete/w_cancel_check_in.php";
            string_user_id = params[1];
            string_place_id = params[2];




            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")+
                        "&" + URLEncoder.encode("place_id", "UTF-8") + "=" + URLEncoder.encode(string_place_id, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_get_health_status_for_main_activity")) {
            String url_register = "http://bespouse.com/wikaya/w_get/get_health_status_for_main_activity.php";
            string_user_id = params[1];




            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_get_link")) {
            String url_register = "http://bespouse.com/wikaya/w_get/get_link.php";
            string_user_id = "";




            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_update_to_hs_clean")) {
            String url_register = "http://bespouse.com/wikaya/w_update/w_update_to_hs_clean.php";
            string_user_id = params[1];




            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_get_registered_places")) {
            String url_register = "http://bespouse.com/wikaya/w_get/w_get_my_places.php";
            string_user_id = params[1];




            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_delete")) {
            String url_register = "http://bespouse.com/wikaya/w_delete/w_delete_place.php";
            string_place_id = params[1];




            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("place_id", "UTF-8") + "=" + URLEncoder.encode(string_place_id, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }else if (method.equals("method_update_profile")) {
            String url_register = "http://bespouse.com/wikaya/w_update/w_update_profile.php";
            string_user_id = params[1];
            string_first_name = params[2];
            string_last_name = params[3];
            string_phone_number = params[4];




            try {
                httpURLConnection = (HttpURLConnection) new URL(url_register).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(string_user_id, "UTF-8")+
                        "&" + URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(string_first_name, "UTF-8")+
                        "&" + URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(string_last_name, "UTF-8")+
                        "&" + URLEncoder.encode("phone_number", "UTF-8") + "=" + URLEncoder.encode(string_phone_number, "UTF-8")
                );
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                input_stream = httpURLConnection.getInputStream();
                buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));
                string_response = "";
                str = "";
                while (true) {
                    str = buffered_reader.readLine();
                    if (str != null) {
                        string_response = string_response + str;
                    } else {
                        buffered_reader.close();
                        input_stream.close();
                        httpURLConnection.disconnect();
                        return string_response;
                    }
                }


            } catch (MalformedURLException e4) {
                e4.printStackTrace();
            } catch (ProtocolException e22) {
                e22.printStackTrace();
            } catch (IOException e32) {
                e32.printStackTrace();
            }


        }



























        return null;
        }




    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(String result) {

        this.delegate.processFinish(result);




    }
    }
