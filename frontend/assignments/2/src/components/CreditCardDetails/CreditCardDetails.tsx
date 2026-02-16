import { useDispatch } from "react-redux"
import { setPaymentDetails } from "../../features/bookingSlice"
import React from "react"
import styles from "./CreditCardDetails.module.scss"

const CreditCardDetails = () => {
    console.log("Rendering CreditCardDetails component")
    const dispatch = useDispatch()

    const handlePaymentDetailsChange = (key: string, value: any) => {
        dispatch(setPaymentDetails({ key, value }))
    }

    const handleCardNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replaceAll(/\s/g, '') 
        value = value.replaceAll(/\D/g, '')
        value = value.slice(0, 16) 
        
        const formatted = value.match(/.{1,4}/g)?.join(' ') || value
        e.target.value = formatted
        handlePaymentDetailsChange("cardNumber", value) 
    }

    const handleExpiryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replaceAll(/\D/g, '') 
        
        if (value.length >= 2) {
            const month = Number.parseInt(value.slice(0, 2))
            if (month > 12) {
                value = '12' + value.slice(2)
            } else if (month === 0) {
                value = '01' + value.slice(2)
            }
            value = value.slice(0, 2) + '/' + value.slice(2, 4)
        }
        
        value = value.slice(0, 5) 
        e.target.value = value
        handlePaymentDetailsChange("expiry", value)
    }

    const handleCvvChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replaceAll(/\D/g, '') 
        value = value.slice(0, 4) 
        e.target.value = value
        handlePaymentDetailsChange("cvv", value)
    }

    const handleNameOnCardChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replaceAll(/[^a-zA-Z\s]/g, '') 
        value = value.replaceAll(/\s{2,}/g, ' ') 
        e.target.value = value
        handlePaymentDetailsChange("nameOnCard", value)
    }

    return (
        <div className={styles.creditCardDetailsContainer}>
            <h3>Credit Card details</h3>

            <div className={styles.cardDetailItems1}>

                    <input
                        id="cardNumber"
                        type="text"
                        inputMode="numeric"
                        autoComplete="cc-number"
                        placeholder="1234 5678 9012 3456"
                        maxLength={16 + 3}
                        onChange={handleCardNumberChange}
                        required
                    />
                    <img src="/src/assets/visaLogo.png" alt="Visa logo" />
            </div>

            <div className={styles.cardDetailItems2}>
                <input
                    id="expiry"
                    type="text"
                    inputMode="numeric"
                    autoComplete="cc-exp"
                    placeholder="MM/YY"
                    maxLength={5}
                    onChange={handleExpiryChange}
                    required
                />
                <input
                    id="cvv"
                    type="password"
                    inputMode="numeric"
                    autoComplete="cc-csc"
                    placeholder="CVV"
                    maxLength={4}
                    onChange={handleCvvChange}
                    required
                />
                <input
                    id="nameOnCard"
                    type="text"
                    autoComplete="cc-name"
                    placeholder="Name as on Card"
                    onChange={handleNameOnCardChange}
                    required
                />
            </div>

        </div>
    )
}

export default React.memo(CreditCardDetails)