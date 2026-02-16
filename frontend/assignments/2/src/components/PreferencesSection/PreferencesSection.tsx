import styles from './PreferencesSection.module.scss';
import { useSelector, useDispatch } from 'react-redux';
import CleaningType from '../CleaningType/CleaningType';
import { CleaningFrequency } from '../CleaningFrequency/CleaningFrequency';
import { HomeDetails } from '../HomeDetails/HomeDetails';
import { ExtraOptions } from '../ExtraOptions/ExtraOptions';
import { HoursAndDates } from '../HoursAndDates/HoursAndDates';
import ConfirmBookingButton from '../ConfirmBookingButton/ConfirmBookingButton';
import CreditCardDetails from '../CreditCardDetails/CreditCardDetails';
import PersonalDetails from '../PersonalDetails/PersonalDetails';
import { validateBooking } from '../../utils/validation';
import { useGetConfigQuery } from '../../services/configApi';
import type { RootState } from '../../app/store';
import { setAcceptedTerms } from '../../features/bookingSlice';

export const PreferencesSection = () => {
    const dispatch = useDispatch();
    const bookingDraft = useSelector((state: RootState) => state.bookingDraft);
    const { data } = useGetConfigQuery();

    const validationResult = validateBooking({
        ...bookingDraft,
    }, data?.constraints);


    return (
        <div className={styles.cleaning_preferences_section}>
            <div className={styles.sectionHeader}><h2>Cleaning Preferences</h2></div>
            {/* component */}
            <CleaningType />

            <CleaningFrequency />

            {/* new component */}

            <HomeDetails />

            {/* Need any extras div */}
            <ExtraOptions />

            {/* special requirement componenets */}

            <div className={styles.specialRequirementsContainer}>
                <p>Do you have any special requirements?</p>
                <textarea placeholder='Write here...'></textarea>
            </div>

            {/* Choose hours and dates Component  */}
            <HoursAndDates />
            {/* Pyment component */}
            <CreditCardDetails />
            {/* Personal Details */}
            <PersonalDetails />

            <div className={styles.termsContainer}>
                <input
                    type="checkbox"
                    name="terms"
                    id="terms"
                    checked={bookingDraft.acceptedTerms}
                    onChange={(e) => dispatch(setAcceptedTerms(e.target.checked))}
                />
                <label htmlFor="terms">I agree to the <span>terms and conditions</span></label>
            </div>

            <ConfirmBookingButton validationResult={validationResult} />

        </div>
    )
}