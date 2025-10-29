# How to Fix Old Credentials Issue

## Problem
You're seeing "tony3000@stark.com" in the audit logs because you're still logged in with the old hardcoded test credentials. Your browser's localStorage still has the old data.

---

## Solution: Logout and Re-login

### Step 1: Logout
1. Click the **"Logout"** button in the top-right corner of the admin dashboard
2. This will clear your localStorage and redirect you to the landing page

### Step 2: Login with Your Real Credentials
1. Go to the login page
2. Enter **YOUR actual email and password** (the one you signed up with)
3. Click "Login"

### Step 3: Verify
1. Go to your **Profile** page
2. Check that it shows **YOUR name, email, phone, and domain**
3. Perform an admin action (approve a request or add a domain)
4. Go to **Audit Logs**
5. You should now see **YOUR email** instead of "tony3000@stark.com"

---

## Alternative: Clear Browser Data Manually

If logout doesn't work, you can manually clear localStorage:

### Option 1: Using Browser Console
1. Open browser DevTools (F12 or Right-click → Inspect)
2. Go to the **Console** tab
3. Type: `localStorage.clear()`
4. Press Enter
5. Refresh the page
6. Login again with your credentials

### Option 2: Using Application Tab
1. Open browser DevTools (F12)
2. Go to **Application** tab (Chrome) or **Storage** tab (Firefox)
3. Click on **Local Storage** → `http://localhost:3000`
4. Right-click and select **Clear**
5. Refresh the page
6. Login again

---

## What Happens When You Logout

The logout function now:
```javascript
authService.logout(); // Clears localStorage
navigate('landing');  // Redirects to landing page
```

This removes:
- JWT token
- User information (email, name, phone, domain)
- Any cached data

---

## After Re-login

Once you login with your actual credentials, localStorage will contain:
```json
{
  "id": 123,
  "email": "your-actual-email@example.com",
  "fullName": "Your Actual Name",
  "role": "ADMIN",
  "phoneNumber": "1234567890",
  "domain": "Finance"
}
```

And all audit logs will show **YOUR** information!

---

## Quick Test

### Before Logout:
- Profile shows: Tony Stark, tony3000@stark.com
- Audit logs show: tony3000@stark.com

### After Re-login:
- Profile shows: YOUR name, YOUR email
- Audit logs show: YOUR email

---

## If You Don't Have an Account Yet

If you haven't signed up with your own credentials:

1. **Logout** from the current session
2. Go to **Sign Up** page
3. Create an account with:
   - Your name
   - Your email
   - Your phone
   - Your domain
   - Your password
   - Role: **ADMIN**
4. **Login** with those credentials
5. Now all actions will be tracked under YOUR name!

---

## Summary

✅ **Click Logout** button (top-right)
✅ **Login again** with YOUR credentials
✅ **Check Profile** to verify it's your data
✅ **Perform an action** (approve/add domain)
✅ **Check Audit Logs** - should show YOUR email now!

**The old "tony3000@stark.com" was just test data. Once you re-login with your real credentials, everything will show your actual information!** 🎉
