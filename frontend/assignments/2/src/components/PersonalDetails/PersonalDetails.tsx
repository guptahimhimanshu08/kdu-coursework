import { useDispatch } from "react-redux"
import { setPersonalDetails } from "../../features/bookingSlice"
import React from "react"
import styles from "./PersonalDetails.module.scss"

const PersonalDetails = () => {
    // console.log("Rendering PersonalDetails component")
    const dispatch = useDispatch()
  

    const handlePersonalDetailsChange = (key: string, value: any) => {
        dispatch(setPersonalDetails({ key, value }))
    }

    const handlePhoneChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replaceAll(/[^0-9+\-()\s]/g, '')
        value = value.slice(0, 10)
        
        
        handlePersonalDetailsChange("phone", value)

       
    }

    const handleZipChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replaceAll(/\D/g, '')
        e.target.value = value
        handlePersonalDetailsChange("zip", value)

    }

    const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value.trim()
        handlePersonalDetailsChange("email", value)
      
    }

    const handleAddressChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replaceAll(/[^a-zA-Z0-9\s,.-]/g, '')
        e.target.value = value
        handlePersonalDetailsChange("address", value)

    }

    return (
        <div className={styles.personalDetailsContainer}>
            <h3>Personal Details</h3>

            <div className={styles.personalDetailItems1}>
                <input
                    id="email"
                    type="email"
                    autoComplete="email"
                    placeholder="Email Address"
                    pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                    onChange={handleEmailChange}
                    required
                />
                <input
                    id="phone"
                    type="tel"
                    autoComplete="tel"
                    placeholder="Phone Number"
                    maxLength={10}
                    onChange={handlePhoneChange}
                    required
                />
            </div>

            <div className={styles.personalDetailItems2}>
                <input
                    id="address"
                    className={styles.addressInput}
                    type="text"
                    autoComplete="street-address"
                    placeholder="Your Full Address"
                    onChange={handleAddressChange}
                    required
                />
                <input
                    id="zip"
                    className={styles.zipInput}
                    type="text"
                    inputMode="numeric"
                    autoComplete="postal-code"
                    placeholder="10023"
                    maxLength={5}
                    onChange={handleZipChange}
                    required
                />
            </div>
        </div>
    )
}
export default React.memo(PersonalDetails)