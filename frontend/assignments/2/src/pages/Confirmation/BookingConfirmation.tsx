import React, { useEffect } from "react";
import { useAddBookingMutation } from "../../services/bookingApi";
import { store } from "../../app/store";
import { resetDraft } from "../../features/bookingSlice";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import styles from "./BookingConfirmation.module.scss";
import Loader from "../../components/loader/Loader";
import ErrorBoundary from "../../components/ErrorBoundary/ErrorBoundary";

export default function BookingConfirmation() {

    const [addBooking, { data, error, isLoading, isSuccess }] = useAddBookingMutation()
    const hasRun = React.useRef(false);
    const navigate = useNavigate();
    const dispatch = store.dispatch;
    const draft = useSelector((state: ReturnType<typeof store.getState>) => state.bookingDraft);

    useEffect(() => {
        if (hasRun.current) return;
        hasRun.current = true;

        if(!draft.acceptedTerms) {
            navigate('/', { replace: true });
            return;
        }
        addBooking(draft);

    }, [addBooking])

    useEffect(() => {
        if (isSuccess) {
            dispatch(resetDraft());
        }
    }, [isSuccess])

    if (isLoading) return (
        <div className={styles.loadingContainer}>
            <Loader />
            <p>Processing your booking...</p>
        </div>
    )
    
    if (error) return (
        <div className={styles.errorContainer}>
            <i className="fas fa-exclamation-circle"></i>
            <h2>Booking Failed</h2>
            <p>Failed to process your booking. Please try again.</p>
            <button onClick={() => navigate('/')}>Return to Booking</button>
        </div>
    )
    
    if (!data) return null;

    const { bookingDetails } = data;

    return (
        <ErrorBoundary>
            <div className={styles.confirmationContainer}>
                <div className={styles.confirmationCard}>
                    <div className={styles.successHeader}>
                    <div className={styles.successIcon}>
                        <i className="fas fa-check-circle"></i>
                    </div>
                    <h1>Booking Confirmed!</h1>
                    <p>Thank you for choosing our cleaning service</p>
                </div>

                <div className={styles.bookingIdSection}>
                    <span className={styles.label}>Booking ID</span>
                    <span className={styles.bookingId}>{data.bookingId}</span>
                </div>

                <div className={styles.detailsGrid}>
                    <div className={styles.detailCard}>
                        <h3><i className="fas fa-broom"></i> Service Details</h3>
                        <div className={styles.detailRow}>
                            <span className={styles.detailLabel}>Cleaning Type:</span>
                            <span className={styles.detailValue}>{typeof bookingDetails.cleaningType === 'object' ? bookingDetails.cleaningType.name : bookingDetails.cleaningType}</span>
                        </div>
                        <div className={styles.detailRow}>
                            <span className={styles.detailLabel}>Frequency:</span>
                            <span className={styles.detailValue}>{typeof bookingDetails.frequency === 'object' ? bookingDetails.frequency.name : bookingDetails.frequency}</span>
                        </div>
                        <div className={styles.detailRow}>
                            <span className={styles.detailLabel}>Duration:</span>
                            <span className={styles.detailValue}>{bookingDetails.hours} {bookingDetails.hours === 1 ? 'hour' : 'hours'}</span>
                        </div>
                    </div>

                    <div className={styles.detailCard}>
                        <h3><i className="fas fa-calendar-alt"></i> Schedule</h3>
                        <div className={styles.detailRow}>
                            <span className={styles.detailLabel}>Date:</span>
                            <span className={styles.detailValue}>{new Date(bookingDetails.date).toLocaleDateString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</span>
                        </div>
                        <div className={styles.detailRow}>
                            <span className={styles.detailLabel}>Start Time:</span>
                            <span className={styles.detailValue}>{bookingDetails.startTime}</span>
                        </div>
                    </div>

                    <div className={styles.detailCard}>
                        <h3><i className="fas fa-home"></i> Property Details</h3>
                        <div className={styles.detailRow}>
                            <span className={styles.detailLabel}>Bedrooms:</span>
                            <span className={styles.detailValue}>{bookingDetails.bedrooms}</span>
                        </div>
                        <div className={styles.detailRow}>
                            <span className={styles.detailLabel}>Bathrooms:</span>
                            <span className={styles.detailValue}>{bookingDetails.bathrooms}</span>
                        </div>
                    </div>

                    <div className={styles.detailCard}>
                        <h3><i className="fas fa-map-marker-alt"></i> Address</h3>
                        <div className={styles.detailRow}>
                            <span className={styles.detailLabel}>Location:</span>
                            <span className={styles.detailValue}>{bookingDetails.addressLine}</span>
                        </div>
                        <div className={styles.detailRow}>
                            <span className={styles.detailLabel}>ZIP Code:</span>
                            <span className={styles.detailValue}>{bookingDetails.zip}</span>
                        </div>
                    </div>
                </div>

                {bookingDetails.extras && bookingDetails.extras.length > 0 && (
                    <div className={styles.extrasSection}>
                        <h3> Additional Services</h3>
                        <div className={styles.extrasList}>
                            {bookingDetails.extras.map((extra: any, index: number) => (
                                <div key={index} className={styles.extraItem}>
                                    <span className={styles.extraName}>
                                        <i className="fas fa-check"></i>
                                        {typeof extra === 'string' ? extra : extra.name}
                                    </span>
                                    {extra.price && (
                                        <span className={styles.extraPrice}>₹{extra.price}</span>
                                    )}
                                </div>
                            ))}
                        </div>
                    </div>
                )}

                <div className={styles.totalSection}>
                    <div className={styles.totalRow}>
                        <span className={styles.totalLabel}>Total Amount</span>
                        <span className={styles.totalPrice}>₹{data.pricing?.total}</span>
                    </div>
                </div>

                <div className={styles.actionButtons}>
                    <button className={styles.primaryButton} onClick={() => navigate('/')}>
                    Book Another Service
                    </button>
                </div>
            </div>
        </div>
        </ErrorBoundary>
    );
}