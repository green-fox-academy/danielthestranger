import pandas as pd

pd.set_option('display.width', 1000)
pd.set_option('display.max_columns', 15)

APP_COL = 'App'
REVIEWS_COL = 'Reviews'

def read_apps(filename):
    def remove_duplicate_rows(dataframe):
        # Apps are repeated for no obvious reason.
        # The review count looks cumulative, so we keep only the records with
        # the highest review count per app.
        indexes_to_keep =\
            dataframe.groupby(by=APP_COL, as_index=False)[REVIEWS_COL].idxmax()
        unique_apps = dataframe.reindex(indexes_to_keep)
        del dataframe
        unique_apps.set_index(APP_COL, inplace=True)
        return unique_apps

    read_data = pd.read_csv(filename)
    unique_rows = remove_duplicate_rows(read_data)
    return unique_rows

def read_reviews(filename):
    return pd.read_csv(filename)

# Edited source CSV: 'Life Made WI-Fi Touchscreen Photo Frame' needed a Category
APPS = read_apps("googleplaystore.csv")
REVIEWS = read_reviews("googleplaystore_user_reviews.csv")


print(APPS)
print(APPS.reindex(['Clash of Clans','Instagram','Life Made WI-Fi Touchscreen Photo Frame'], copy=False))
