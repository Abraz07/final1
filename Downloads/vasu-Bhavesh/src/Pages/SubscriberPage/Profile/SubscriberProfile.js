import React, { useState, useEffect } from 'react';
import { Mail, Phone, Briefcase, Edit2, Save, X } from 'lucide-react';
import './SubscriberProfile.css';
import authService from '../../../services/authService';
import axios from 'axios';

const SubscriberProfile = () => {
    const [editing, setEditing] = useState(false);
    const [data, setData] = useState({
        name: '', 
        email: '', 
        phone: '',
        domain: '', 
        department: ''
    });
    const [temp, setTemp] = useState({ ...data });
    const [loading, setLoading] = useState(true);

    // Load user data from localStorage on component mount
    useEffect(() => {
        const user = authService.getCurrentUser();
        if (user) {
            const userProfile = {
                name: user.fullName || '',
                email: user.email || '',
                phone: user.phoneNumber || '',
                domain: user.domain || '',
                department: user.domain || '' // Using domain as department for now
            };
            setData(userProfile);
            setTemp(userProfile);
        }
        setLoading(false);
    }, []);

    const fields = [
        { icon: Mail, label: 'Email', key: 'email', type: 'email' },
        { icon: Phone, label: 'Phone', key: 'phone', type: 'tel' },
        { icon: Briefcase, label: 'Domain', key: 'domain', type: 'text' }
    ];

    const stats = [
        { label: 'Reports Downloaded', value: '142', color: '#0473BA' },
        { label: 'Favorite Reports', value: '3', color: '#38D200' }
    ];

    if (loading) {
        return (
            <div className="profile-container">
                <h1 className="profile-title">My Profile</h1>
                <div style={{ textAlign: 'center', padding: '50px' }}>
                    <div className="spinner-border text-primary" role="status">
                        <span className="visually-hidden">Loading...</span>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="profile-container">
            <h1 className="profile-title">My Profile</h1>
            <br></br>
            <div className="profile-card">
                <div className="profile-header">
                    <div className="header-info">
                        {editing ? (
                            <input className="name-input" value={temp.name} 
                                onChange={(e) => setTemp({ ...temp, name: e.target.value })} />
                        ) : <h2>{data.name}</h2>}
                        <p>Subscriber</p>
                    </div>
                    {!editing ? (
                        <button className="btn-edit" onClick={() => { setEditing(true); setTemp({ ...data }); }}>
                            <Edit2 size={16} /> Edit
                        </button>
                    ) : (
                        <div className="edit-btns">
                            <button className="btn-save" onClick={() => { setData({ ...temp }); setEditing(false); }}>
                                <Save size={16} /> Save
                            </button>
                            <button className="btn-cancel" onClick={() => { setTemp({ ...data }); setEditing(false); }}>
                                <X size={16} /> Cancel
                            </button>
                        </div>
                    )}
                </div>

                <div className="stats">
                    {stats.map((s, i) => (
                        <div key={i} className="stat" style={{ borderLeftColor: s.color }}>
                            <div className="stat-val" style={{ color: s.color }}>{s.value}</div>
                            <div className="stat-label">{s.label}</div>
                        </div>
                    ))}
                </div>

                <div className="info-section">
                    <h3>Personal Information</h3>
                    <div className="info-grid">
                        {fields.map((f, i) => (
                            <div key={i} className="info-item">
                                <div className="icon"><f.icon size={20} /></div>
                                <div>
                                    <label>{f.label}</label>
                                    {editing ? (
                                        <input type={f.type} value={temp[f.key]} 
                                            onChange={(e) => setTemp({ ...temp, [f.key]: e.target.value })} />
                                    ) : <p>{data[f.key]}</p>}
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SubscriberProfile;