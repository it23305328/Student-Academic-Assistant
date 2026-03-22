<template>
  <div class="otp-container">
    <div class="otp-card">
      <div class="otp-header">
        <h2 class="otp-title">Secure Verification</h2>
        <p class="otp-subtitle">We'll send a 6-digit code to your phone</p>
      </div>

      <div class="form-group">
        <label for="phone">Phone Number</label>
        <div class="input-wrapper">
          <span class="prefix">+94</span>
          <input 
            id="phone" 
            v-model="phoneNumber" 
            type="tel" 
            placeholder="771234567" 
            :disabled="otpSent"
            class="phone-input"
          />
        </div>
      </div>

      <div v-if="otpSent" class="form-group animate-in">
        <label for="otp">Enter 6-Digit Code</label>
        <div class="otp-input-group">
          <input 
            v-for="(digit, index) in 6" 
            :key="index"
            :id="'otp-' + index"
            v-model="otpDigits[index]"
            type="text"
            maxlength="1"
            class="digit-input"
            @input="handleOtpInput($event, index)"
            @keydown.backspace="handleBackspace(index)"
          />
        </div>
      </div>

      <div class="action-footer">
        <button 
          @click="otpSent ? handleVerify() : handleSendOtp()" 
          :disabled="loading"
          class="btn-primary"
        >
          <span v-if="loading" class="loader"></span>
          <span v-else>{{ otpSent ? 'Verify Code' : 'Send OTP' }}</span>
        </button>
        
        <button v-if="otpSent" @click="resetFlow" class="btn-link">
          Change Phone Number
        </button>
      </div>

      <div v-if="message" :class="['status-msg', isError ? 'error' : 'success']">
        {{ message }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import apiClient from '../services/api';

const phoneNumber = ref('');
const otpSent = ref(false);
const loading = ref(false);
const message = ref('');
const isError = ref(false);
const otpDigits = reactive(['', '', '', '', '', '']);

const handleSendOtp = async () => {
    if (!phoneNumber.value) {
        showMessage('Please enter a phone number', true);
        return;
    }

    loading.value = true;
    try {
        const fullPhone = '+94' + phoneNumber.value;
        const response = await apiClient.post('/api/auth/send-otp', {
            phoneNumber: fullPhone
        });
        otpSent.value = true;
        showMessage('OTP sent successfully!', false);
    } catch (error) {
        showMessage(error.response?.data?.message || 'Failed to send OTP', true);
    } finally {
        loading.value = false;
    }
};

const handleVerify = async () => {
    const otp = otpDigits.join('');
    if (otp.length < 6) {
        showMessage('Please enter all 6 digits', true);
        return;
    }

    loading.value = true;
    try {
        const fullPhone = '+94' + phoneNumber.value;
        const response = await apiClient.post('/api/auth/verify-otp', {
            phoneNumber: fullPhone,
            otp: otp
        });
        if (response.data.success) {
            showMessage('Verification successful!', false);
            // Handle success (e.g. redirect to dashboard or next step)
        }
    } catch (error) {
        showMessage(error.response?.data?.message || 'Invalid OTP', true);
    } finally {
        loading.value = false;
    }
};

const handleOtpInput = (event, index) => {
    const val = event.target.value;
    if (val && index < 5) {
        document.getElementById('otp-' + (index + 1)).focus();
    }
};

const handleBackspace = (index) => {
    if (!otpDigits[index] && index > 0) {
        document.getElementById('otp-' + (index - 1)).focus();
    }
};

const resetFlow = () => {
    otpSent.value = false;
    otpDigits.fill('');
    message.value = '';
};

const showMessage = (msg, err) => {
    message.value = msg;
    isError.value = err;
    setTimeout(() => {
        if (!otpSent.value || !err) message.value = '';
    }, 5000);
};
</script>

<style scoped>
.otp-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: #f8fafc;
    font-family: 'Manrope', sans-serif;
}

.otp-card {
    background: white;
    padding: 2.5rem;
    border-radius: 20px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
    width: 100%;
    max-width: 400px;
    border: 1px solid #e2e8f0;
}

.otp-header {
    margin-bottom: 2rem;
    text-align: center;
}

.otp-title {
    color: #4e6073;
    font-size: 1.5rem;
    font-weight: 800;
    margin-bottom: 0.5rem;
}

.otp-subtitle {
    color: #64748b;
    font-size: 0.875rem;
}

.form-group {
    margin-bottom: 1.5rem;
}

label {
    display: block;
    margin-bottom: 0.5rem;
    color: #475569;
    font-size: 0.875rem;
    font-weight: 600;
}

.input-wrapper {
    display: flex;
    align-items: center;
    border: 2px solid #e2e8f0;
    border-radius: 12px;
    padding: 0 1rem;
    transition: border-color 0.2s;
}

.input-wrapper:focus-within {
    border-color: #4e6073;
}

.prefix {
    color: #64748b;
    font-weight: 700;
    padding-right: 0.5rem;
    border-right: 1px solid #e2e8f0;
}

.phone-input {
    border: none;
    padding: 0.75rem;
    flex: 1;
    font-family: inherit;
    font-size: 1rem;
    color: #1e293b;
    outline: none;
}

.otp-input-group {
    display: flex;
    gap: 0.5rem;
    justify-content: space-between;
}

.digit-input {
    width: 3.5rem;
    height: 3.5rem;
    text-align: center;
    font-size: 1.25rem;
    font-weight: 700;
    border: 2px solid #e2e8f0;
    border-radius: 12px;
    outline: none;
    transition: all 0.2s;
}

.digit-input:focus {
    border-color: #4e6073;
    box-shadow: 0 0 0 4px rgba(78, 96, 115, 0.1);
}

.action-footer {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin-top: 2rem;
}

.btn-primary {
    background-color: #4e6073;
    color: white;
    border: none;
    padding: 1rem;
    border-radius: 12px;
    font-weight: 700;
    font-size: 1rem;
    cursor: pointer;
    transition: opacity 0.2s, transform 0.1s;
}

.btn-primary:active {
    transform: scale(0.98);
}

.btn-primary:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.btn-link {
    background: none;
    border: none;
    color: #64748b;
    font-size: 0.875rem;
    font-weight: 600;
    cursor: pointer;
    text-decoration: underline underline-offset-4;
}

.status-msg {
    margin-top: 1.5rem;
    padding: 0.75rem;
    border-radius: 8px;
    font-size: 0.875rem;
    text-align: center;
}

.success {
    background-color: #f0fdf4;
    color: #166534;
    border: 1px solid #bbf7d0;
}

.error {
    background-color: #fef2f2;
    color: #991b1b;
    border: 1px solid #fecaca;
}

.animate-in {
    animation: slideUp 0.3s ease-out;
}

@keyframes slideUp {
    from { opacity: 0; transform: translateY(10px); }
    to { opacity: 1; transform: translateY(0); }
}

.loader {
    width: 20px;
    height: 20px;
    border: 3px solid rgba(255,255,255,0.3);
    border-radius: 50%;
    border-top-color: #fff;
    animation: spin 1s ease-in-out infinite;
    display: inline-block;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}
</style>
