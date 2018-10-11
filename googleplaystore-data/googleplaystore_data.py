import pandas as pd

pd.set_option('display.width', 1000)
pd.set_option('display.max_columns', 15)

APP_COL = 'App'
REVIEWS_COL = 'Reviews'

def read_apps(filename):
    def translate_str_col_to_numeric_inplace(dataframe, column_label):
        # Reviews col has "3.0M".
        # TODO implement a more generic solution - seems difficult
        dataframe[column_label].\
            replace(to_replace="3.0M", value=3000000, inplace=True)
        dataframe[column_label] =\
            pd.to_numeric(dataframe[column_label], errors='coerce')

    def remove_duplicate_rows(dataframe, new_index_column_label,
                              max_column_label):
        # Apps are repeated for no obvious reason.
        # The review count looks cumulative, so we keep only the records with
        # the highest review count per app.
        indexes_to_keep =\
            dataframe.groupby(by=new_index_column_label, as_index=False)\
            [max_column_label].idxmax()
        unique_apps = dataframe.reindex(indexes_to_keep)
        del dataframe
        unique_apps.set_index(new_index_column_label, inplace=True)
        return unique_apps

    read_data = pd.read_csv(filename)
    # Reviews col has dodgy data - a problem, as we use it for selecting
    # unique records.
    translate_str_col_to_numeric_inplace(read_data, REVIEWS_COL)
    unique_rows = remove_duplicate_rows(read_data, APP_COL, REVIEWS_COL)
    return unique_rows

def read_reviews(filename):
    return pd.read_csv(filename)


APPS = read_apps("googleplaystore.csv")
REVIEWS = read_reviews("googleplaystore_user_reviews.csv")


print(APPS)
print(APPS.reindex(['Clash of Clans','Instagram','Life Made WI-Fi Touchscreen Photo Frame'], copy=False))
