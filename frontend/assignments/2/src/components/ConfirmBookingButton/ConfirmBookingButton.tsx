import { useNavigate } from "react-router-dom";
import type { ValidationResult } from "../../utils/validation";
import styles from "./ConfirmBookingButton.module.scss";
import React from "react";

interface ConfirmBookingButtonProps {
    validationResult: ValidationResult;
}

const ConfirmBookingButton = ({ validationResult }: ConfirmBookingButtonProps) => {
    const navigate = useNavigate();
    console.log(validationResult)
    console.log("Rendering Button");
    
    const handleClick = () => {
        if (validationResult.ok) {
            navigate('/booking/confirmation');
        }
    }

    
    const iconMap: Record<string, string> = {
        lock: "https://img.icons8.com/ios-filled/24/000000/lock--v1.png"
    };

    return(
        <div className={styles.confirmBookingButtonContainer}>
            {validationResult.ok ? (
                <button onClick={handleClick} className={styles.confirmBookingButton}>
                    <img src={iconMap.lock} alt="lock" />
                    <span>Complete Booking via Secure Servers</span>
                </button>
            ) : (
                <button disabled className={styles.confirmBookingButtonDisabled}>
                    <img src={iconMap.lock} alt="lock" />
                    <span>Complete Booking via Secure Servers</span>
                </button>
            )}
        </div>
    )
}

export default React.memo(ConfirmBookingButton);