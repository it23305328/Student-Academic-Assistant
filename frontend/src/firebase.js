import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

// Your web app's Firebase configuration from your Firebase Console
// Replace these dummy values with your actual Firebase project configurations!
const firebaseConfig = {
  apiKey: "AIzaSyDtMEqKq0na-HWNRoxqIKEKO0TbDZY6jtk",
  authDomain: "itpmproject-f2656.firebaseapp.com",
  projectId: "itpmproject-f2656",
  storageBucket: "itpmproject-f2656.firebasestorage.app",
  messagingSenderId: "610656006086",
  appId: "1:610656006086:web:31d5435322b71695af8cc7",
  measurementId: "G-55JTNG1X09"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

// Initialize Firebase Authentication and get a reference to the service
export const auth = getAuth(app);
