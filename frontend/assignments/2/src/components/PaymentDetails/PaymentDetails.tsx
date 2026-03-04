import CreditCardDetails  from "../CreditCardDetails/CreditCardDetails"
import  PersonalDetails  from "../PersonalDetails/PersonalDetails"
import styles from "./PaymentDetails.module.scss"

export const PaymentDetails = () => {
    return (
        <div className={styles.paymentDetailsContainer}>
            <div className={styles.paymentHeader}>
                <h2>Payment Method</h2>
                <div className={styles.securityInfo}>
                    <span>🔒</span>
                    <div className={styles.securityText}>
                        <div>256-bit Secure</div>
                        <div>SSL Encryption</div>
                    </div>
                </div>
            </div>

            <hr />

            {/* Credit Card Details */}
            <CreditCardDetails />

            {/* Personal Details */}
            <PersonalDetails />

        </div>
    )
}