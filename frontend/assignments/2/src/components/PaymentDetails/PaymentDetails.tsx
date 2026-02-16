import CreditCardDetails  from "../CreditCardDetails/CreditCardDetails"
import  PersonalDetails  from "../PersonalDetails/PersonalDetails"

export const PaymentDetails = () => {
    return (
        <div>
            <div>
                <h2>Payment Method</h2>
                <div>
                    <span>🔒</span>
                    <div>
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