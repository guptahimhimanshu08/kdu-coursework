import { useEffect } from "react";
import { useAppDispatch, useAppSelector } from "../../app/store";
import { fetchStatusThunk } from "../../slices/statusSlice";
import styles from "./BookingConfirmation.module.scss";

export const BookingConfirmation = () => {
    
    const { registrationId, status, name, email, eventName, loading, error } = useAppSelector((state) => state.status);
    
    // console.log("Rendering Booking Confirmation for ID:", registrationId);
    
    const dispatch = useAppDispatch();

    useEffect(() => {
        if (registrationId) {
            dispatch(fetchStatusThunk(registrationId));
        }
    }, [registrationId, dispatch]);

    return (
        <div className={styles.container}>
            <div className={styles.heading}>
            <h1>Registration Status</h1>
            </div>
            {error && <p style={{ color: "red" }}>Error: {error}</p>}
            {!loading && !error && (
                <div className={styles.detailsContainer}>
                <h3>Registration ID: {registrationId}</h3>
                    
                <div className={styles.userDetails}>
                    <p>Name: {name}</p>
                    <p>Email: {email}</p>
                    <p>Event: {eventName}</p>
                </div>
                <div className={status === "QUEUED" ? styles.pending : status === "SUCCESSFULL" ? styles.confirmed : styles.rejected}>
                    <p>Status: <span>
                        {status}
                        </span></p>
                        
                    <button className={styles.refreshButton} onClick={() => dispatch(fetchStatusThunk(registrationId))}>Refresh</button>
                </div>
                    {loading && <p>Loading...</p>}
                </div>
            )}
        </div>
    );
}